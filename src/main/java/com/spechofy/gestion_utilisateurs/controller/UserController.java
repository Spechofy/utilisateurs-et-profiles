package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /**
     * Creates a new user with locale and location information.
     * If locale is not provided in the request body, it will be extracted from the Accept-Language header.
     * If location data is provided, the locationUpdatedAt timestamp will be set.
     * 
     * @param user The user data from the request body
     * @param acceptLanguage The Accept-Language header from the request
     * @return The created user
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user, 
                                          @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        // If locale is not set, extract it from the Accept-Language header
        if (user.getLocale() == null || user.getLocale().isEmpty()) {
            user.setLocale(extractLocaleFromHeader(acceptLanguage));
        }

        // If location data is provided, set the locationUpdatedAt timestamp
        if (user.getLatitude() != null && user.getLongitude() != null) {
            user.setLocationUpdatedAt(LocalDateTime.now());
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Updates a user's locale.
     * 
     * @param userId The ID of the user to update
     * @param locale The new locale value
     * @return The updated user
     */
    @PutMapping("/users/{userId}/locale")
    public ResponseEntity<User> updateUserLocale(@PathVariable Long userId, @RequestBody String locale) {
        return userRepository.findById(userId)
            .map(user -> {
                // Remove quotes if present
                String cleanLocale = locale.replaceAll("^\"|\"$", "");
                user.setLocale(cleanLocale);
                User updatedUser = userRepository.save(user);
                return ResponseEntity.ok(updatedUser);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Gets a user's locale.
     * 
     * @param userId The ID of the user
     * @return The user's locale
     */
    @GetMapping("/users/{userId}/locale")
    public ResponseEntity<String> getUserLocale(@PathVariable Long userId) {
        return userRepository.findById(userId)
            .map(user -> ResponseEntity.ok(user.getLocale()))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a user's location.
     * 
     * @param userId The ID of the user to update
     * @param locationData Map containing latitude and longitude
     * @return The updated user
     */
    @PutMapping("/users/{userId}/location")
    public ResponseEntity<User> updateUserLocation(@PathVariable Long userId, @RequestBody Map<String, Double> locationData) {
        Double latitude = locationData.get("latitude");
        Double longitude = locationData.get("longitude");

        if (latitude == null || longitude == null) {
            return ResponseEntity.badRequest().build();
        }

        return userRepository.findById(userId)
            .map(user -> {
                user.setLatitude(latitude);
                user.setLongitude(longitude);
                user.setLocationUpdatedAt(LocalDateTime.now());
                User updatedUser = userRepository.save(user);
                return ResponseEntity.ok(updatedUser);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Gets a user's location.
     * 
     * @param userId The ID of the user
     * @return Map containing the user's latitude, longitude, and location update timestamp
     */
    @GetMapping("/users/{userId}/location")
    public ResponseEntity<Map<String, Object>> getUserLocation(@PathVariable Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                Map<String, Object> locationData = Map.of(
                    "latitude", user.getLatitude(),
                    "longitude", user.getLongitude(),
                    "locationUpdatedAt", user.getLocationUpdatedAt()
                );
                return ResponseEntity.ok(locationData);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Extracts the locale from the Accept-Language header.
     * 
     * @param acceptLanguage The Accept-Language header value
     * @return The extracted locale, or "en" as default if not available
     */
    private String extractLocaleFromHeader(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return "en"; // Default locale
        }

        // The Accept-Language header can contain multiple languages with quality values
        // e.g., "fr-CH, fr;q=0.9, en;q=0.8, de;q=0.7, *;q=0.5"
        // We'll take the first one (highest priority)
        String[] languages = acceptLanguage.split(",");
        if (languages.length > 0) {
            String primaryLanguage = languages[0].trim();
            // If there's a quality value, remove it
            if (primaryLanguage.contains(";")) {
                primaryLanguage = primaryLanguage.substring(0, primaryLanguage.indexOf(";")).trim();
            }
            return primaryLanguage;
        }

        return "en"; // Default locale
    }
}
