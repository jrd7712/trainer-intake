package com.example.trainerintake.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // ✅ auto-generated PK
    private Long surveyId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)       // ✅ FK to User.id
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @OneToOne(mappedBy = "survey", cascade = CascadeType.ALL)
    private WorkoutPlan workoutPlan;

    @Column(name = "survey_number")
    private Long surveyNumber;

    // getters/setters
    public Long getSurveyId() { return surveyId; }
    public void setSurveyId(Long surveyId) { this.surveyId = surveyId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Answer> getAnswers() { return answers; }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    public WorkoutPlan getWorkoutPlan() { return workoutPlan; }
    public void setWorkoutPlan(WorkoutPlan workoutPlan) { this.workoutPlan = workoutPlan; }

    public Long getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(Long surveyNumber) {
        this.surveyNumber = surveyNumber;
    }
}