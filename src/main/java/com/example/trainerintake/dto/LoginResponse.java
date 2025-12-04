package com.example.trainerintake.dto;

public class LoginResponse {
    private String token;
    public LoginResponse(String token) { this.token = token; }


    // Getter and Setter for token
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for message
    public String getMessage() {
        return token;
    }
    public void setMessage(String message) {
        this.token = message;
    }
}