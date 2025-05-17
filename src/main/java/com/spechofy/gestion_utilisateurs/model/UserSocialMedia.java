package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Objects;

@Entity
@Data
@IdClass(UserSocialMediaId.class)
public class UserSocialMedia {
    @Id
    private Long userId;

    @Id
    private Long socialMediaId;

    private String profileLink;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("socialMediaId")
    @JoinColumn(name = "social_media_id")
    @JsonIgnore
    private SocialMedia socialMedia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSocialMedia that = (UserSocialMedia) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(socialMediaId, that.socialMediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, socialMediaId);
    }
}

