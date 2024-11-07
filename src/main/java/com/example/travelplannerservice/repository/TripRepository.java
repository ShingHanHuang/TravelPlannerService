package com.example.travelplannerservice.repository;

import com.example.travelplannerservice.dao.Trip;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface TripRepository extends Neo4jRepository<Trip, String> {
    List<Trip> findByIsShared(boolean isShared);
}
