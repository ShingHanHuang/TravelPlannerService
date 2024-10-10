package com.example.travelplannerservice.dto;

public class LoginResponseDTO {
    private final boolean success;
    private final String jwt;
    private final String username;

    public LoginResponseDTO(boolean success, String jwt, String username) {
        this.success = success;
        this.jwt = jwt;
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }
}
