package com.example.trainerintake.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "prompt")  // ✅ this is the actual question text
    private String questionText;

    @Column(name = "section")   // ✅ new column
    private String section;


    // Getters and setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}