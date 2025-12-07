package com.example.trainerintake.service;

import java.time.LocalDateTime;

import com.example.trainerintake.model.Question;
import com.example.trainerintake.model.Answer;
import com.example.trainerintake.model.Survey;
import com.example.trainerintake.model.User;
import com.example.trainerintake.model.WorkoutPlan;
import com.example.trainerintake.repository.QuestionRepository;
import com.example.trainerintake.repository.AnswerRepository;
import com.example.trainerintake.repository.SurveyRepository;
import com.example.trainerintake.repository.WorkoutPlanRepository;
import com.example.trainerintake.service.PlanService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SurveyRepository surveyRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final PlanService planService;

    public SurveyService(QuestionRepository questionRepository,
                         AnswerRepository answerRepository,
                         SurveyRepository surveyRepository,
                         WorkoutPlanRepository workoutPlanRepository,
                        PlanService planService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.surveyRepository = surveyRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.planService = planService;
    }

    // Get all survey questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Create a new survey for a user and save answers
     public Survey createSurveyWithAnswers(User user, List<Answer> answers) {
        // 1. Create survey
        Survey survey = new Survey();
        survey.setUser(user);
        survey.setCreatedAt(LocalDateTime.now());
        survey = surveyRepository.save(survey);

        // 2. Save answers
        for (Answer answer : answers) {
            answer.setSurvey(survey);
        }
        answerRepository.saveAll(answers);

        // 3. Generate workout plan text using AI
        String aiPlanText = planService.generatePlanTextFromSurvey(survey.getSurveyId());

        // 4. Create workout plan entry
        WorkoutPlan plan = new WorkoutPlan();
        plan.setSurvey(survey);
        plan.setPlanText(aiPlanText); // ✅ AI text instead of placeholder
        plan.setCreatedAt(LocalDateTime.now());
        workoutPlanRepository.save(plan);

    return survey;
}




    // Get answers for a specific survey
    public List<Answer> getAnswersForSurvey(Integer surveyId) { // ✅ use Long
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found: " + surveyId));

        return answerRepository.findBySurvey(survey);
    }
}