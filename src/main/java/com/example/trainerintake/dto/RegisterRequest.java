package com.example.trainerintake.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;   // ✅ must match User.username

    // Getters and Setters
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}