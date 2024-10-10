package com.example.travelplannerservice.controller;

import com.example.travelplannerservice.dto.TripRequestDTO;
import com.example.travelplannerservice.dto.TripResponseDTO;
import com.example.travelplannerservice.dao.Trip;
import com.example.travelplannerservice.response.ApiResponse;
import com.example.travelplannerservice.service.LangchainService;
import com.example.travelplannerservice.service.TripService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/trip")
public class TripController {
    @Autowired
    private ModelMapper modelMapper;
    private final LangchainService langchainService;
    private final TripService tripService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public TripController(LangchainService langchainService, TripService tripService) {
        this.langchainService = langchainService;
        this.tripService = tripService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<String>> generateTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        String itinerary = langchainService.generateItinerary(
                tripRequestDTO.getDestination(),
                tripRequestDTO.getPreferences(),
                tripRequestDTO.getTravelStartDates(),
                tripRequestDTO.getTravelEndDates(),
                tripRequestDTO.getTravelCompanions(),
                tripRequestDTO.getAccommodationType()
        );
        ApiResponse<String> response = new ApiResponse<>(true, itinerary, "Itinerary generated successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> saveTrip(@RequestParam String userId,
                                                        @RequestBody TripRequestDTO tripRequestDTO) {
        Trip trip = modelMapper.map(tripRequestDTO, Trip.class);
        String savedTripId = tripService.saveTrip(userId, trip);
        ApiResponse<String> response = new ApiResponse<>(true, savedTripId, "Trip saved successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{trip_id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(@PathVariable("trip_id") String tripId,
                                                        @RequestParam("userId") String userId) {
        try {
            tripService.deleteTrip(userId, tripId);
            ApiResponse<Void> response = new ApiResponse<>(true, null, "Trip saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while deleting trip", e);
            ApiResponse<Void> response = new ApiResponse<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TripResponseDTO>>> listUserTrips(@RequestParam String userId) {
        logger.info("Fetching trips for user {}", userId);
        List<Trip> trips = tripService.getUserTrips(userId);
        List<TripResponseDTO> tripDTOs = trips.stream()
                .map(trip -> modelMapper.map(trip, TripResponseDTO.class))
                .toList();
        ApiResponse<List<TripResponseDTO>> response = new ApiResponse<>(true, tripDTOs, "Trips fetched successfully");
        return ResponseEntity.ok(response);
    }
}
