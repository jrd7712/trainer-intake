package com.example.trainerintake.repository;

import java.util.List;
import java.util.Optional;

import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.model.Survey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {
    Optional<WorkoutPlan> findBySurvey(Survey survey);

    // ✅ New method: fetch all workout plans tied to surveys for a given user
    List<WorkoutPlan> findBySurvey_User_Id(Integer userId);
}