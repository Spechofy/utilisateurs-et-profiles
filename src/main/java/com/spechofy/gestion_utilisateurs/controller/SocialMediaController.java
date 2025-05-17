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

@RestController
public class SocialMediaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private UserSocialMediaRepository userSocialMediaRepository;

    @GetMapping("/allsocials")
    public List<SocialMedia> getAllSocialMedias() {
        return socialMediaRepository.findAll();
    }

    @PostMapping("/users/{userId}/addSocialMedia")
    public ResponseEntity<String> addOrUpdateSocialMediaToUser(
            @PathVariable Long userId,
            @RequestBody UserSocialMedia userSocialMediaRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid userId");
        }

        SocialMedia socialMedia = socialMediaRepository.findById(userSocialMediaRequest.getSocialMediaId()).orElse(null);

        if (socialMedia == null) {
            return ResponseEntity.badRequest().body("Invalid socialMediaId");
        }

        // Check if the social media link already exists for the user
        Optional<UserSocialMedia> existingLinkOpt = userSocialMediaRepository.findByUserIdAndSocialMediaId(userId, userSocialMediaRequest.getSocialMediaId());

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
