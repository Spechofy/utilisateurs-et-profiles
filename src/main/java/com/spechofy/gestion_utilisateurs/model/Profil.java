package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing a user profile in the system.
 * 
 * This class contains profile information such as photo, biography,
 * and additional personal information.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profil {
    /**
     * Unique identifier for the profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profilId;

    /**
     * URL or path to the user's profile photo.
     */
    private String photo;

    /**
     * User's biography or personal description.
     */
    private String biography;

    /**
     * The user this profile belongs to.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * Additional personal information associated with this profile.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id")
    private Information information;
}
