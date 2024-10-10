package com.example.travelplannerservice.dto;

import java.util.List;

public class TripRequestDTO {
    private String destination;
    private List<String> preferences;
    private String travelStartDates;
    private String travelEndDates;
    private String travelCompanions;
    private String accommodationType;
    private String itinerary;
    public TripRequestDTO() {
    }

    public TripRequestDTO(String destination, List<String> preferences
            , String travelStartDates, String travelEndDates
            , String travelCompanions, String accommodationType, String itinerary) {
        this.destination = destination;
        this.preferences = preferences;
        this.travelStartDates = travelStartDates;
        this.travelEndDates = travelEndDates;
        this.travelCompanions = travelCompanions;
        this.accommodationType = accommodationType;
        this.itinerary= itinerary;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public String getTravelStartDates() {
        return travelStartDates;
    }

    public String getTravelEndDates() {
        return travelEndDates;
    }

    public String getTravelCompanions() {
        return travelCompanions;
    }

    public String getAccommodationType() {
        return accommodationType;
    }
    public String getItinerary() {
        return itinerary;
    }
}
