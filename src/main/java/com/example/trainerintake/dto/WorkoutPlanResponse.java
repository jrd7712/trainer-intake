package com.example.trainerintake.dto;

import java.util.List;

public class WorkoutPlanResponse {
    private Long surveyId;
    private List<String> answers;
    private String workoutPlan;

    public WorkoutPlanResponse(Long surveyId, List<String> answers, String workoutPlan) {
        this.surveyId = surveyId;
        this.answers = answers;
        this.workoutPlan = workoutPlan;
    }

    public Long getSurveyId() { return surveyId; }
    public List<String> getAnswers() { return answers; }
    public String getWorkoutPlan() { return workoutPlan; }
}