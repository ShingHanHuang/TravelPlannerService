package com.example.travelplannerservice.repository;

import com.example.travelplannerservice.dao.Trip;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TripRepository extends Neo4jRepository<Trip, String> {
}
