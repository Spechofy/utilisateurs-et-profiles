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

@RestController
public class ProfilController {

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allprofiles")
    public List<Profil> getAllProfiles() {
        return profilRepository.findAll();
    }

    @GetMapping("/allinfo")
    public List<Information> getAllInformations() {
        return informationRepository.findAll();
    }

    @PostMapping("/users/{userId}/addProfile")
    public ResponseEntity<String> addOrUpdateProfileToUser(@PathVariable Long userId, @RequestBody Profil profilRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid userId");
        }

        Profil existingProfil = profilRepository.findByUser_UserId(userId).orElse(null);

        if (existingProfil != null) {
            // Mise à jour du profil existant
            existingProfil.setBiography(profilRequest.getBiography());
            existingProfil.setPhoto(profilRequest.getPhoto());
            existingProfil.setInformation(profilRequest.getInformation());
            profilRepository.save(existingProfil);
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            // Création d’un nouveau profil
            profilRequest.setUser(user);
            profilRepository.save(profilRequest);
            return ResponseEntity.ok("Profile created successfully");
        }
    }

}
