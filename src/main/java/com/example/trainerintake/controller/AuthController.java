package com.example.trainerintake.controller;

import java.util.Optional;

import com.example.trainerintake.dto.LoginRequest;
import com.example.trainerintake.dto.LoginResponse;
import com.example.trainerintake.dto.RegisterRequest;
import com.example.trainerintake.dto.RegisterResponse;
import com.example.trainerintake.model.User;
import com.example.trainerintake.service.UserService;
import com.example.trainerintake.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
        public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            User saved = userService.saveUser(user);

            RegisterResponse response = new RegisterResponse(
               "Registration successful",
             true,
                saved.getId(),
                saved.getUsername()
            );

    return ResponseEntity.ok(response);
}

    // Login endpoint
    @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            User user = userService.findByUsername(loginRequest.getUsername())
                                   .orElseThrow(() -> new RuntimeException("User not found"));

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user);
                return ResponseEntity.ok(new LoginResponse(token, "Login successful"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
}