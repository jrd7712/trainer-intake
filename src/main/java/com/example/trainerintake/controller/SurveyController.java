package com.example.trainerintake.controller;

import com.example.trainerintake.dto.AnswerRequest;
import com.example.trainerintake.dto.QuestionDTO;
import com.example.trainerintake.model.Answer;
import com.example.trainerintake.model.Question;
import com.example.trainerintake.model.Survey;
import com.example.trainerintake.model.User;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.service.SurveyService;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.repository.QuestionRepository;
import com.example.trainerintake.repository.WorkoutPlanRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public SurveyController(SurveyService surveyService,
                            UserRepository userRepository,
                            QuestionRepository questionRepository,
                            WorkoutPlanRepository workoutPlanRepository) {
        this.surveyService = surveyService;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    // ⭐ Convert Question → QuestionDTO
    private QuestionDTO toDTO(Question q) {
        List<String> parsedChoices = null;

        try {
            if (q.getChoices() != null) {
                parsedChoices = new ObjectMapper().readValue(
                        q.getChoices(),
                        new TypeReference<List<String>>() {}
                );
            }
        } catch (Exception e) {
            parsedChoices = null;
        }

        return new QuestionDTO(
                q.getQuestionId(),
                q.getQuestionText(),
                q.getSection(),
                q.getInputType(),
                parsedChoices
        );
    }

    // ⭐ 1. Get all questions (now returns DTOs)
    @GetMapping("/questions")
    public List<QuestionDTO> getQuestions() {
        return surveyService.getAllQuestions()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 2. Submit answers by userId (legacy)
    @PostMapping("/submit/{userId}")
    public Map<String, Object> submitAnswersByUserId(@PathVariable Long userId,
                                                     @RequestBody List<Answer> answers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Survey survey = surveyService.createSurveyWithAnswers(user, answers);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("surveyId", survey.getSurveyId());
        response.put("message", "Survey submitted successfully");
        return response;
    }

    // 3. Get answers for a survey by ID
    @GetMapping("/{id}")
    public Map<String, Object> getSurvey(@PathVariable Long id) {
        List<Answer> answers = surveyService.getAnswersForSurvey(id);

        Map<String, Object> response = new HashMap<>();
        response.put("survey_id", id);
        response.put("answers", answers);
        return response;
    }

    // 4. Submit answers using JWT principal
    @PostMapping("/submit")
    public Map<String, Object> submitAnswers(@RequestBody List<AnswerRequest> answerRequests,
                                             Principal principal) {

        User user = userRepository.findByEmailIgnoreCase(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Answer> answers = answerRequests.stream()
                .map(req -> {
                    Answer a = new Answer();
                    a.setResponse(req.getResponse());

                    Question question = questionRepository.findById(req.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("Question not found"));
                    a.setQuestion(question);

                    return a;
                })
                .collect(Collectors.toList());

        Survey survey = surveyService.createSurveyWithAnswers(user, answers);

        WorkoutPlan plan = workoutPlanRepository.findBySurvey(survey)
                .orElseThrow(() -> new RuntimeException("Workout plan not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("surveyId", survey.getSurveyId());
        response.put("message", "Survey submitted successfully");
        response.put("workoutPlan", plan.getPlanText());
        return response;
    }
}