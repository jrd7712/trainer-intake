package com.example.trainerintake.repository;

import com.example.trainerintake.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); 
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);

}