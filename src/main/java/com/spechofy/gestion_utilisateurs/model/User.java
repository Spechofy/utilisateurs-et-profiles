package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * Entity representing a user in the system.
 * 
 * This class contains basic user information and relationships to profiles
 * and social media accounts.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * User's pseudonym or username.
     */
    private String pseudo;

    /**
     * User's date of birth.
     */
    private LocalDate birthDate;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's phone number.
     */
    private String phone;

    /**
     * User's profile information.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profil profil;

    /**
     * User's social media connections.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserSocialMedia> socialMediaLinks;
}
