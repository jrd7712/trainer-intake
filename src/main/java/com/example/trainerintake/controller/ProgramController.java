package com.example.trainerintake.controller;

import com.example.trainerintake.dto.MyPrograms;
import com.example.trainerintake.model.User;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.repository.WorkoutPlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                        p.getSurvey() != null ? p.getSurvey().getSurveyNumber() : (Long) null,
                        p.getPlanText(),
                        p.getCreatedAt()
                ))
                .toList();
    }

    // ⭐ NEW: Delete a program owned by the current user
    @DeleteMapping("/{programId}")
    public ResponseEntity<?> deleteProgram(
            @PathVariable Long programId,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutPlan plan = workoutPlanRepo.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        // Ensure the program belongs to the authenticated user
        if (!plan.getSurvey().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You cannot delete a program that does not belong to you");
        }

        workoutPlanRepo.delete(plan);

        return ResponseEntity.ok("Program deleted successfully");
    }
}