package com.example.trainerintake.controller;

import com.example.trainerintake.model.User;
import com.example.trainerintake.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Register a new client
    @PostMapping("/register")
    public User registerClient(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 2. Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getClient(@PathVariable Integer id) {
        return userService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get all clients
    @GetMapping
    public List<User> getAllClients() {
        return userService.getAllClients();
    }

    // 4. Delete client by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        userService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Get client by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getClientByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}