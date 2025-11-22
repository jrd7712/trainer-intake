package com.example.trainerintake.service;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanService {

    private final AnswerRepository answerRepo;

    public PlanService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    public Map<String, Object> buildPlanFromSurvey(Integer surveyId) {
        List<Answer> answers = answerRepo.findBySurveyId(surveyId);

        Map<String, Object> plan = new HashMap<>();
        plan.put("survey_id", surveyId);
        plan.put("answers", answers);

        // Placeholder: later you’ll call your AI layer here
        plan.put("workout_plan", "Generated workout plan will go here");

        return plan;
    }
}