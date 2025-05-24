package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.Objects;

/**
 * Entity representing a connection between a user and a social media platform.
 * 
 * This class serves as a join table with additional data (profileLink) to connect
 * users with their social media accounts.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserSocialMediaId.class)
public class UserSocialMedia {
    /**
     * Part of the composite primary key - the user ID.
     */
    @Id
    private Long userId;

    /**
     * Part of the composite primary key - the social media platform ID.
     */
    @Id
    private Long socialMediaId;

    /**
     * URL to the user's profile on the social media platform.
     */
    private String profileLink;

    /**
     * The user who owns this social media connection.
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * The social media platform this connection refers to.
     */
    @ManyToOne
    @MapsId("socialMediaId")
    @JoinColumn(name = "social_media_id")
    @JsonIgnore
    private SocialMedia socialMedia;

    /**
     * Custom equals method for composite key comparison.
     * 
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSocialMedia that = (UserSocialMedia) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(socialMediaId, that.socialMediaId);
    }

    /**
     * Custom hashCode method for composite key.
     * 
     * @return hash code based on the composite key fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, socialMediaId);
    }
}
