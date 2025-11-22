package com.example.trainerintake.service;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.model.Question;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.repository.AnswerRepository;
import com.example.trainerintake.repository.QuestionRepository;
import com.example.trainerintake.repository.WorkoutPlanRepository;
import com.example.trainerintake.dto.WorkoutPlanResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PlanService {

    private final AnswerRepository answerRepo;
    private final QuestionRepository questionRepo;
    private final WorkoutPlanRepository workoutPlanRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    public PlanService(AnswerRepository answerRepo,
                       QuestionRepository questionRepo,
                       WorkoutPlanRepository workoutPlanRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
        this.workoutPlanRepo = workoutPlanRepo;
    }

    public WorkoutPlanResponse buildPlanFromSurvey(Integer surveyId) {
        List<Answer> answers = answerRepo.findBySurveyId(surveyId);

        // 🔑 Serialize answers into readable prompt
        List<String> answerStrings = new ArrayList<>();
        StringBuilder promptBuilder = new StringBuilder();
        for (Answer a : answers) {
            Question q = questionRepo.findById(a.getQuestionId()).orElse(null);
            String line = (q != null)
                ? q.getQuestionText() + ": " + a.getAnswerText()
                : "Q" + a.getQuestionId() + ": " + a.getAnswerText();
            answerStrings.add(line);
            promptBuilder.append(line).append("\n");
        }
        String surveyText = promptBuilder.toString();

        // 🔑 Build OpenAI request
        Map<String, Object> payload = Map.of(
            "model", "gpt-4.1-mini",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a fitness coach."),
                Map.of("role", "user", "content", "Generate a workout plan based on these survey answers:\n" + surveyText)
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        Map<String, Object> response = restTemplate.postForObject(apiUrl, request, Map.class);

        // 🔑 Extract AI response
        String workoutPlan = "";
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            workoutPlan = (String) message.get("content");
        } catch (Exception e) {
            workoutPlan = "Error parsing AI response: " + e.getMessage();
        }

        // 🔑 Save to database
        WorkoutPlan plan = new WorkoutPlan();
        plan.setSurveyId(surveyId);
        plan.setPlanText(workoutPlan);
        plan.setCreatedAt(LocalDateTime.now());
        workoutPlanRepo.save(plan);

        // 🔑 Return typed DTO
        return new WorkoutPlanResponse(surveyId, answerStrings, workoutPlan);
    }
}