package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Entity representing a social media platform.
 * 
 * This class contains information about social media platforms that users
 * can connect to their profiles.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialMedia {
    /**
     * Unique identifier for the social media platform.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialMediaId;

    /**
     * Name of the social media platform.
     */
    private String plateformeName;

    /**
     * Links to users who have connected this social media platform.
     */
    @OneToMany(mappedBy = "socialMedia", cascade = CascadeType.ALL)
    private Set<UserSocialMedia> userLinks;
}
