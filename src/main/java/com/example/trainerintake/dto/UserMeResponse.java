package com.example.trainerintake.dto;

public record UserMeResponse(
    Long id,
    String username,
    String firstName,
    String lastName,
    String email
) {}