package com.example.travelplannerservice.controller;

import com.example.travelplannerservice.JwtUtil;
import com.example.travelplannerservice.service.UserService;
import com.example.travelplannerservice.dao.User;
import com.example.travelplannerservice.dto.LoginResponseDTO;
import com.example.travelplannerservice.dto.UserDTO;
import com.example.travelplannerservice.response.ApiResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager
            , JwtUtil jwtUtil, GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@RequestBody UserDTO userDTO) {
        logger.info("registerUser" + userDTO.getUsername());
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), "USER");
        userService.saveUser(user);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, userDTO, "User registered successfully!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        LoginResponseDTO responseDTO = new LoginResponseDTO(true, jwt, userDTO.getUsername());
        logger.info("User {} successfully logged in. JWT: {}", userDTO.getUsername(), jwt);
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(true, responseDTO, "Login successful");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/google-login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> googleLogin(@RequestBody Map<String, String> request) throws GeneralSecurityException, IOException {
        String token = request.get("token");

        GoogleIdToken idToken = googleIdTokenVerifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
//                String userId = payload.getSubject();
            String email = payload.getEmail();
//                boolean emailVerified = payload.getEmailVerified();
            User user = new User(email, "", "USER");
            userService.saveUser(user);
            String jwt = jwtUtil.generateToken(email);
            LoginResponseDTO responseDTO = new LoginResponseDTO(true, jwt, email);
            ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(true, responseDTO, "Login successful");
            return ResponseEntity.ok(apiResponse);
        } else {
            ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(false, null, "Invalid ID token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }
    }

}

