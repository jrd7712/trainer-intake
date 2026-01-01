package com.example.trainerintake.controller;

import com.example.trainerintake.dto.MyPrograms;
import com.example.trainerintake.model.User;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.repository.WorkoutPlanRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<MyPrograms> getProgramsForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<WorkoutPlan> plans = workoutPlanRepo.findBySurvey_User_Id(user.getId());

        return plans.stream()
                .map(p -> new MyPrograms(
                        p.getId(),
                        p.getSurvey().getSurveyId(),
                        p.getPlanText(),
                        p.getCreatedAt()
                ))
                .toList();
    }
}