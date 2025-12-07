package com.example.trainerintake.dto;

public class AnswerRequest {
    private Integer surveyId;
    private Integer questionId;
    private String response;
    // getters and setters
    public Integer getSurveyId() {
        return surveyId;
    }
    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }
    public Integer getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
}