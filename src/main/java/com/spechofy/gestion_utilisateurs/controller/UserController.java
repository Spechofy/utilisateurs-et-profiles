package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing users.
 * 
 * This controller provides endpoints for retrieving user information
 * and testing authentication.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Protected endpoint that requires authentication.
     * 
     * @return A simple message indicating this is a protected route
     */
    @GetMapping("/me")
    public String getGreeting() {
        return "protected route";
    }

    /**
     * Public endpoint that doesn't require authentication.
     * 
     * @return A simple greeting message
     */
    @GetMapping("/public")
    public String getPublic() {
        return "what's up";
    }

    /**
     * Retrieves all users in the system.
     * 
     * @return List of all users
     */
    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
