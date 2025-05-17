package com.spechofy.gestion_utilisateurs.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserSocialMediaTest {

    @Test
    void testEqualsAndHashCode() {
        UserSocialMediaId id1 = new UserSocialMediaId();
        id1.setUserId(1L);
        id1.setSocialMediaId(2L);

        UserSocialMediaId id2 = new UserSocialMediaId();
        id2.setUserId(1L);
        id2.setSocialMediaId(2L);

        UserSocialMedia userSocialMedia1 = new UserSocialMedia();
        userSocialMedia1.setUserId(1L);
        userSocialMedia1.setSocialMediaId(2L);

        UserSocialMedia userSocialMedia2 = new UserSocialMedia();
        userSocialMedia2.setUserId(1L);
        userSocialMedia2.setSocialMediaId(2L);

        assertThat(userSocialMedia1).isEqualTo(userSocialMedia2);
        assertThat(userSocialMedia1.hashCode()).isEqualTo(userSocialMedia2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        UserSocialMedia userSocialMedia = new UserSocialMedia();
        userSocialMedia.setUserId(1L);
        userSocialMedia.setSocialMediaId(2L);
        userSocialMedia.setProfileLink("http://example.com");

        assertThat(userSocialMedia.getUserId()).isEqualTo(1L);
        assertThat(userSocialMedia.getSocialMediaId()).isEqualTo(2L);
        assertThat(userSocialMedia.getProfileLink()).isEqualTo("http://example.com");
    }

    @Test
    void testEqualsFailsForDifferentObjects() {
        UserSocialMedia userSocialMedia1 = new UserSocialMedia();
        userSocialMedia1.setUserId(1L);
        userSocialMedia1.setSocialMediaId(2L);

        UserSocialMedia userSocialMedia2 = new UserSocialMedia();
        userSocialMedia2.setUserId(3L);
        userSocialMedia2.setSocialMediaId(4L);

        assertThat(userSocialMedia1).isNotEqualTo(userSocialMedia2);
    }
}
