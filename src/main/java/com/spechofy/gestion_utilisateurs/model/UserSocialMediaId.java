package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSocialMediaId implements Serializable {
    private Long userId;
    private Long socialMediaId;

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for socialMediaId
    public Long getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(Long socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSocialMediaId that = (UserSocialMediaId) o;
        return userId.equals(that.userId) && socialMediaId.equals(that.socialMediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, socialMediaId);
    }
}

