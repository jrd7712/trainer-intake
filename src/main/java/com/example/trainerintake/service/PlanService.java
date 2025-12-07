package com.example.trainerintake.service;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.model.Question;
import com.example.trainerintake.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PlanService {

    private final AnswerRepository answerRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    public PlanService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    /**
     * Generate workout plan text from survey answers using AI.
     * This method does NOT save anything to the database.
     */
    public String generatePlanTextFromSurvey(Integer surveyId) {
        List<Answer> answers = answerRepo.findBySurvey_SurveyId(surveyId);

        // Build prompt
        StringBuilder promptBuilder = new StringBuilder("Generate a workout plan based on these survey answers:\n");
        for (Answer a : answers) {
            Question q = a.getQuestion();
            promptBuilder.append(q != null ? q.getQuestionText() : "Unknown Question")
                         .append(": ")
                         .append(a.getResponse())
                         .append("\n");
        }
        String surveyText = promptBuilder.toString();

        // Call AI
        Map<String, Object> payload = Map.of(
            "model", "gpt-4.1-mini",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a fitness coach."),
                Map.of("role", "user", "content", surveyText)
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        Map<String, Object> response = restTemplate.postForObject(apiUrl, request, Map.class);

        String workoutPlan = "";
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                if (message != null) {
                    workoutPlan = (String) message.get("content");
                }
            }
        } catch (Exception e) {
            workoutPlan = "Error parsing AI response: " + e.getMessage();
        }

        return workoutPlan;
    }
}