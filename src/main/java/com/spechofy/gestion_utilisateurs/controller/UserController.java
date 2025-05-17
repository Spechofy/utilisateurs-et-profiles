package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public String getGreeting() {
        return "protected route";
    }

    @GetMapping("/public")
    public String getPublic(){
        return "what's up";
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
