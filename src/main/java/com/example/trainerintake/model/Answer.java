package com.example.trainerintake.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    private Integer surveyId;
    private Integer questionId;
    private String answerText;

    // Getters and setters
    public Integer getAnswerId() { return answerId; }
    public void setAnswerId(Integer answerId) { this.answerId = answerId; }

    public Integer getSurveyId() { return surveyId; }
    public void setSurveyId(Integer surveyId) { this.surveyId = surveyId; }

    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }

    public String getAnswerText() { return answerText; }
    public void setAnswerText(String answerText) { this.answerText = answerText; }
}