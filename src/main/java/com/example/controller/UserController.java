package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // ✅ Allow frontend requests (adjust as needed)
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        logger.info("Received registration request for username: {}", user.getUsername());

        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            logger.warn("Username already exists: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Username already taken"));
        }

        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            logger.info("User registered successfully: {}", user.getUsername());
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        }
        logger.error("User registration failed for username: {}", user.getUsername());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User registration failed"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        logger.info("Received login request for username: {}", user.getUsername());

        Optional<User> foundUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (foundUser.isPresent()) {
            logger.info("User logged in successfully: {}", user.getUsername());
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            logger.warn("Invalid credentials for username: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }

    // ✅ Add this test endpoint to check if the backend is running
    @GetMapping("/test")
    public ResponseEntity<?> testEndpoint() {
        return ResponseEntity.ok(Map.of("message", "Backend is working!"));
    }
}

