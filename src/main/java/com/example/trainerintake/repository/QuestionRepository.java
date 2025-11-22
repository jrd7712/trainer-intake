package com.example.trainerintake.repository;

import com.example.trainerintake.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}