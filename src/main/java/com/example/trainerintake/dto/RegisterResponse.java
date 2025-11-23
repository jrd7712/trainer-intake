package com.example.trainerintake.dto;

public class RegisterResponse {
    private String message;
    private boolean success;

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}