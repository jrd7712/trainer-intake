package com.example.trainerintake.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String response;

    // ✅ Link to Survey
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    // ✅ Link to Question
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;


    // Getters and setters
    public Integer getAnswerId() { return answerId; }
    public void setAnswerId(Integer answerId) { this.answerId = answerId; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey survey) { this.survey = survey; }  // ✅ setter you need

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    
}