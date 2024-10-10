package com.example.travelplannerservice.dto;

import java.time.LocalDate;
import java.util.List;


public class TripResponseDTO {
    private String id; // Trip ID
    private String destination;
    private List<String> preferences;
    private String itinerary;
    private LocalDate startDate;
    private LocalDate endDate;

    public TripResponseDTO() {

    }

    public TripResponseDTO(String id, String destination, List<String> preferences,
                           String itinerary, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.destination = destination;
        this.preferences = preferences;
        this.itinerary = itinerary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

