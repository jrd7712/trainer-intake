package com.example.trainerintake.repository;

import com.example.trainerintake.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findBySurveyId(Integer surveyId);
}