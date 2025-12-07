package com.example.trainerintake.repository;

import java.util.Optional;

import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.model.Survey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {
    Optional<WorkoutPlan> findBySurvey(Survey survey);
}

