package com.example.trainerintake.controller;

import java.util.Optional;

import com.example.trainerintake.dto.LoginRequest;
import com.example.trainerintake.dto.LoginResponse;
import com.example.trainerintake.dto.RegisterRequest;
import com.example.trainerintake.dto.RegisterResponse;
import com.example.trainerintake.dto.UserMeResponse;
import com.example.trainerintake.model.User;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.service.UserService;
import com.example.trainerintake.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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

        @GetMapping("/me")
            public ResponseEntity<UserMeResponse> getCurrentUser() {
                var auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth == null || !auth.isAuthenticated()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                String email = auth.getName();

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UserMeResponse dto = new UserMeResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
                );      
                return ResponseEntity.ok(dto);
            }

    }