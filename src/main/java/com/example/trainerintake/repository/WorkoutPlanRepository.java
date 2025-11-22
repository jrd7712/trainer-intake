package com.example.trainerintake.repository;

import com.example.trainerintake.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {
    WorkoutPlan findBySurveyId(Integer surveyId);
}