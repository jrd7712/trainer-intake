package com.example.trainerintake.repository;

import com.example.trainerintake.model.Answer;
import com.example.trainerintake.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Query by Survey entity reference
    List<Answer> findBySurvey(Survey survey);

    // Alternatively, query by surveyId directly
    List<Answer> findBySurvey_SurveyId(Long surveyId);
}

