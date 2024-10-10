package com.example.travelplannerservice.dto;

public class UserDTO {
    private final String username;
    private final String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
