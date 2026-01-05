package com.example.trainerintake.dto;

import java.util.List;

public class QuestionDTO {
    private Long questionId;
    private String questionText;
    private String section;
    private String inputType;
    private List<String> choices;

    public QuestionDTO(Long questionId, String questionText, String section, String inputType, List<String> choices) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.section = section;
        this.inputType = inputType;
        this.choices = choices;
    }

    public Long getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public String getSection() { return section; }
    public String getInputType() { return inputType; }
    public List<String> getChoices() { return choices; }
}