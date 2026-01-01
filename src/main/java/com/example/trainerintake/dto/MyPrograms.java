package com.example.trainerintake.dto;

import java.time.LocalDateTime;

public record MyPrograms(
        Long id,
        Integer surveyId,
        String planText,
        LocalDateTime createdAt
) {}