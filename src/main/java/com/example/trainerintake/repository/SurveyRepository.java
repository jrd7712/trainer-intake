package com.example.trainerintake.repository;

import com.example.trainerintake.model.Survey;
import com.example.trainerintake.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    // Find all surveys for a given user
    List<Survey> findByUser(User user);

    // Find the latest survey for a user
    Survey findTopByUserOrderByCreatedAtDesc(User user);
    @Query("SELECT COALESCE(MAX(s.surveyNumber), 0) FROM Survey s WHERE s.user.id = :userId")
    Long findLastSurveyNumberForUser(@Param("userId") Long userId);
}