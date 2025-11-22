package com.example.trainerintake.service;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PlanService {

    private final AnswerRepository answerRepo;
    private final RestTemplate restTemplate;

    public PlanService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> buildPlanFromSurvey(Integer surveyId) {
        // 1. Get answers from DB
        List<Answer> answers = answerRepo.findBySurveyId(surveyId);

        // 2. Build payload to send to AI stub
        Map<String, Object> payload = new HashMap<>();
        payload.put("survey_id", surveyId);
        payload.put("answers", answers);

        // 3. Call Flask stub (running on localhost:5000)
        String workoutPlan = restTemplate.postForObject(
            "http://localhost:5000/generate-plan",
            payload,
            String.class
        );

        // 4. Build response object
        Map<String, Object> plan = new HashMap<>();
        plan.put("survey_id", surveyId);
        plan.put("answers", answers);
        plan.put("workout_plan", workoutPlan);

        return plan;
    }
}