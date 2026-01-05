package com.example.trainerintake.dto;

import java.time.LocalDateTime;

public record MyPrograms(
        Long id,
        Long surveyNumber,
        String planText,
        LocalDateTime createdAt
) {}