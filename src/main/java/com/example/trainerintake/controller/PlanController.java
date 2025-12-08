package com.example.trainerintake.controller;

import com.example.trainerintake.service.PlanService;
import com.example.trainerintake.dto.WorkoutPlanResponse;



import org.springframework.web.bind.annotation.*;

import java.util.Collections;



@RestController
@RequestMapping("/generate-plan")
public class PlanController {

    private final PlanService planService;
    

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{id}")
    public WorkoutPlanResponse generatePlan(@PathVariable Integer id) {
        // Call the new method that returns just the AI text
        String aiPlanText = planService.generatePlanTextFromSurvey(id);

        // Wrap it in a DTO for consistency
        return new WorkoutPlanResponse(id, Collections.emptyList(), aiPlanText);
    }
}