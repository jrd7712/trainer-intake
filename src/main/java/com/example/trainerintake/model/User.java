package com.example.trainerintake.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Clients")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")

    private Long id;   // ✅ keep Integer for consistency

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)   // ✅ enforce not null
    private String password; // hashed with BCrypt

    @Column(name = "first_name", nullable = false)   // ✅ map to DB column
    private String firstName;

    @Column(name = "last_name", nullable = false)   // ✅ map to DB column
    private String lastName;

    // ✅ One user can have many surveys
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public List<Survey> getSurveys() { return surveys; }
    public void setSurveys(List<Survey> surveys) { this.surveys = surveys; }
}