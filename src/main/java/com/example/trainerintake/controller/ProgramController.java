package com.example.trainerintake.controller;


import com.example.trainerintake.service.PlanService;
import com.example.trainerintake.dto.WorkoutPlanResponse;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.repository.WorkoutPlanRepository;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepo;

    public ProgramController(UserRepository userRepository, WorkoutPlanRepository workoutPlanRepo) {
        this.userRepository = userRepository;
        this.workoutPlanRepo = workoutPlanRepo;
    }

    @GetMapping("/me")
        public List<WorkoutPlan> getProgramsForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
            // ✅ use userDetails.getUsername(), since you set email as the JWT subject
            User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            return workoutPlanRepo.findBySurvey_User_Id(user.getId());
        }


}

