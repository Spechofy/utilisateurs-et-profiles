package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.Information;
import com.spechofy.gestion_utilisateurs.model.Profil;
import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.InformationRepository;
import com.spechofy.gestion_utilisateurs.repository.ProfilRepository;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * REST controller for managing user profiles.
 * 
 * This controller provides endpoints for creating, retrieving, and updating
 * user profiles and their associated information.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@RestController
public class ProfilController {

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all user profiles.
     * 
     * @return List of all profiles in the system
     */
    @GetMapping("/allprofiles")
    public List<Profil> getAllProfiles() {
        return profilRepository.findAll();
    }

    /**
     * Retrieves all user information records.
     * 
     * @return List of all information records in the system
     */
    @GetMapping("/allinfo")
    public List<Information> getAllInformations() {
        return informationRepository.findAll();
    }

    /**
     * Adds or updates a profile for a specific user.
     * 
     * @param userId The ID of the user to add/update the profile for
     * @param profilRequest The profile data to be added or updated
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/users/{userId}/addProfile")
    public ResponseEntity<String> addOrUpdateProfileToUser(
            @PathVariable final Long userId, 
            @RequestBody final Profil profilRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid userId");
        }

        Profil existingProfil = profilRepository.findByUser_UserId(userId).orElse(null);

        if (existingProfil != null) {
            // Update existing profile
            existingProfil.setBiography(profilRequest.getBiography());
            existingProfil.setPhoto(profilRequest.getPhoto());
            existingProfil.setInformation(profilRequest.getInformation());
            profilRepository.save(existingProfil);
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            // Create a new profile
            profilRequest.setUser(user);
            profilRepository.save(profilRequest);
            return ResponseEntity.ok("Profile created successfully");
        }
    }

}
