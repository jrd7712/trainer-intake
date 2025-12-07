package com.example.trainerintake.dto;

public class LoginResponse {
    private String token;
    private String message;   // ✅ separate field for message

    // Constructor
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}