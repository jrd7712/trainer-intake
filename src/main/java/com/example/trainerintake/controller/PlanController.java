package com.example.trainerintake.controller;

import com.example.trainerintake.service.PlanService;
import com.example.trainerintake.dto.WorkoutPlanResponse;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/generate-plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{id}")
    public WorkoutPlanResponse generatePlan(@PathVariable Integer id) {
        return planService.buildPlanFromSurvey(id);
    }
}

