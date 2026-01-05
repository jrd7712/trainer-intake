package com.example.trainerintake.dto;

public class RegisterResponse {
    private String message;
    private boolean success;
    private Long userId;     // ✅ optional: expose the new user’s ID
    private String username;    // ✅ optional: expose the new user’s username

    // Constructors
    public RegisterResponse() {}

    public RegisterResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public RegisterResponse(String message, boolean success, Long userId, String username) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.username = username;
    }

    // Getters and Setters
    public String getMessage() { 
        return message; 
    }
    public void setMessage(String message) { 
        this.message = message; 
    }

    public boolean isSuccess() { 
        return success; 
    }
    public void setSuccess(boolean success) { 
        this.success = success; 
    }

    public Long getUserId() { 
        return userId; 
    }
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
}