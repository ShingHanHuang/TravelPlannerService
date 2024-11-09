package com.example.travelplannerservice.service;

import com.example.travelplannerservice.config.SecurityConfig;
import com.example.travelplannerservice.dao.User;
import com.example.travelplannerservice.dao.Trip;
import com.example.travelplannerservice.exception.TripNotFoundException;
import com.example.travelplannerservice.exception.UserNotFoundException;
import com.example.travelplannerservice.repository.TripRepository;
import com.example.travelplannerservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    public TripService(UserRepository userRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    public String saveTrip(String userId, Trip trip) {
        trip.setId(UUID.randomUUID().toString());
        logger.debug("Saving trip: {}", trip);
        tripRepository.save(trip);
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.getTrips().add(trip);
        userRepository.save(user);

        return trip.getId();
    }

    public void updateTrip(String userId, String tripId, Trip trip) {
        trip.setId(tripId);
        tripRepository.save(trip);
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        List<Trip> trips = user.getTrips();
        for (Trip trip1 : trips) {
            logger.debug(" trip  : {}", trip1.getDestination());
            logger.debug(" trip Id : {}", trip1.getId());
        }
        user.getTrips().remove(trip);
        user.getTrips().add(trip);
        userRepository.save(user);

    }

    public void deleteTrip(String userId, String tripId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));

        if (!user.getTrips().contains(trip)) {
            throw new TripNotFoundException("Trip does not belong to the user with ID: " + userId);
        }

        user.getTrips().remove(trip);
        userRepository.save(user);
        tripRepository.delete(trip);

        logger.info("Trip {} deleted for user {}", tripId, userId);
    }

    public List<Trip> getUserTrips(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return user.getTrips();
    }

    public Trip getTripById(String userId, String tripId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));

        if (!user.getTrips().contains(trip)) {
            throw new TripNotFoundException("Trip does not belong to the user with ID: " + userId);
        }
        return trip;
    }

    public void setTripAsShared(String poster, String tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripId));
        trip.setPoster(poster);
        trip.setShared(true);
        tripRepository.save(trip);
    }

    public List<Trip> getAllSharedTrips() {
        return tripRepository.findByIsShared(true);
    }
}
