package com.example.trainerintake.controller;

import com.example.trainerintake.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/generate-plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> generatePlan(@PathVariable Integer id) {
        return planService.buildPlanFromSurvey(id);
    }
}