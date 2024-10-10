package com.example.travelplannerservice.repository;

import com.example.travelplannerservice.dao.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, String> {
    Optional<User> findByUsername(String username);
}
