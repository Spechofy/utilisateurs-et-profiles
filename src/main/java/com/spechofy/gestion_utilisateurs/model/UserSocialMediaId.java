package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key class for the UserSocialMedia entity.
 * 
 * This class represents the composite primary key consisting of userId and socialMediaId
 * for the UserSocialMedia join table.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialMediaId implements Serializable {
    /**
     * User ID part of the composite key.
     */
    private Long userId;

    /**
     * Social media ID part of the composite key.
     */
    private Long socialMediaId;

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
        UserSocialMediaId that = (UserSocialMediaId) o;
        return userId.equals(that.userId) && socialMediaId.equals(that.socialMediaId);
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
