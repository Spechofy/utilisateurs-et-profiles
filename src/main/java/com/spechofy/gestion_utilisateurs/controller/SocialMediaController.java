package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.SocialMedia;
import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.model.UserSocialMedia;
import com.spechofy.gestion_utilisateurs.repository.SocialMediaRepository;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import com.spechofy.gestion_utilisateurs.repository.UserSocialMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing social media connections.
 * 
 * This controller provides endpoints for creating, retrieving, and updating
 * social media connections for users.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@RestController
public class SocialMediaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private UserSocialMediaRepository userSocialMediaRepository;

    /**
     * Retrieves all social media platforms.
     * 
     * @return List of all social media platforms in the system
     */
    @GetMapping("/allsocials")
    public List<SocialMedia> getAllSocialMedias() {
        return socialMediaRepository.findAll();
    }

    /**
     * Adds or updates a social media connection for a specific user.
     * 
     * @param userId The ID of the user to add/update the social media connection for
     * @param userSocialMediaRequest The social media connection data to be added or updated
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/users/{userId}/addSocialMedia")
    public ResponseEntity<String> addOrUpdateSocialMediaToUser(
            @PathVariable final Long userId,
            @RequestBody final UserSocialMedia userSocialMediaRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid userId");
        }

        SocialMedia socialMedia = socialMediaRepository.findById(userSocialMediaRequest.getSocialMediaId()).orElse(null);

        if (socialMedia == null) {
            return ResponseEntity.badRequest().body("Invalid socialMediaId");
        }

        // Check if the social media link already exists for the user
        Optional<UserSocialMedia> existingLinkOpt = userSocialMediaRepository.findByUserIdAndSocialMediaId(
                userId, userSocialMediaRequest.getSocialMediaId());

        if (existingLinkOpt.isPresent()) {
            UserSocialMedia existingLink = existingLinkOpt.get();
            existingLink.setProfileLink(userSocialMediaRequest.getProfileLink());
            userSocialMediaRepository.save(existingLink);
            return ResponseEntity.ok("Social media link updated successfully");
        } else {
            userSocialMediaRequest.setUser(user);
            userSocialMediaRequest.setSocialMedia(socialMedia);
            userSocialMediaRepository.save(userSocialMediaRequest);
            return ResponseEntity.ok("Social media link added successfully");
        }
    }
}
