package com.example.trainerintake.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "prompt")
    private String questionText;

    @Column(name = "section")
    private String section;

    @Column(name = "input_type")   // ⭐ text, multiple_choice, number, etc.
    private String inputType;

    @Column(columnDefinition = "TEXT")   // ⭐ JSON array of choices
    private String choices;

    // Getters and setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getInputType() { return inputType; }
    public void setInputType(String inputType) { this.inputType = inputType; }

    public String getChoices() { return choices; }
    public void setChoices(String choices) { this.choices = choices; }
}