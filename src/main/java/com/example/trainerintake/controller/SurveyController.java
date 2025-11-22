package com.example.trainerintake.controller;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.service.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getSurvey(@PathVariable Integer id) {
        List<Answer> answers = surveyService.getAnswersForSurvey(id);

        Map<String, Object> response = new HashMap<>();
        response.put("survey_id", id);
        response.put("answers", answers);

        return response;
    }
}