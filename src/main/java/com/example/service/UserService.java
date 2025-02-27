package com.example.service;

import com.example.model.User;
import java.util.Optional;

public interface UserService {  // Changed from class to interface

    Optional<User> findByUsername(String username);

    User registerUser(User user);

    Optional<User> loginUser(String username, String password);
}

