package com.example.trainerintake.controller;

import com.example.trainerintake.dto.LoginRequest;
import com.example.trainerintake.dto.LoginResponse;
import com.example.trainerintake.dto.RegisterRequest;
import com.example.trainerintake.dto.RegisterResponse;
import com.example.trainerintake.model.User;
import com.example.trainerintake.repository.UserRepository;
import com.example.trainerintake.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // ✅ Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        // Hash password
        String hashed = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hashed);
        user.setName(request.getName());

        userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setMessage("User registered successfully");
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }
}