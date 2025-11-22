package com.example.trainerintake.service;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {
    private final AnswerRepository answerRepo;

    public SurveyService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    public List<Answer> getAnswersForSurvey(Integer surveyId) {
        return answerRepo.findBySurveyId(surveyId);
    }
}