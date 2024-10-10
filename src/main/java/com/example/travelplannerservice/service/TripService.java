package com.example.travelplannerservice.service;

import com.example.travelplannerservice.config.SecurityConfig;
import com.example.travelplannerservice.dao.User;
import com.example.travelplannerservice.dao.Trip;
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

    public String saveTrip(String userId,Trip trip) {
        trip.setId(UUID.randomUUID().toString());
        logger.warn("trip" + trip.toString());
        tripRepository.save(trip);

        // Link trip to user
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getTrips().add(trip);
        userRepository.save(user);

        return trip.getId();
    }

    public List<Trip> getUserTrips(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getTrips();
    }
}
