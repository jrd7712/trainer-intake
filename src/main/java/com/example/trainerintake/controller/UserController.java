package com.example.trainerintake.controller;

import com.example.trainerintake.model.User;
import com.example.trainerintake.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerClient(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getClient(@PathVariable Long id) {
        return userService.getClientById(id);
    }

    @GetMapping
    public List<User> getAllClients() {
        return userService.getAllClients();
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        userService.deleteClient(id);
    }

    @GetMapping("/email/{email}")
    public User getClientByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}