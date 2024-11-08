package com.example.travelplannerservice.dao;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDate;
import java.util.List;

@Node("Trip")
public class Trip {

    @Id
    private String id; // Trip ID
    private String destination;
    private List<String> preferences;
    private String itinerary;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isShared=false;
    private String poster;

    public Trip() {
    }

    public Trip(String id, String destination, List<String> preferences, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.destination = destination;
        this.preferences = preferences;
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

    public void setShared(boolean shared) {
        isShared = shared;
    }
    public boolean isShared() {
        return isShared;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trip) {
            Trip trip = (Trip) obj;
            if (this.id == null) {
                return false;
            } else
                return this.id.equals(trip.id);
        } else
            return false;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", destination='" + destination + '\'' +
                ", preferences=" + preferences +
                ", itinerary='" + itinerary + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}