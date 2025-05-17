package com.spechofy.gestion_utilisateurs.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserSocialMediaIdTest {

    @Test
    void testEqualsAndHashCode() {
        UserSocialMediaId id1 = new UserSocialMediaId();
        id1.setUserId(1L);
        id1.setSocialMediaId(2L);

        UserSocialMediaId id2 = new UserSocialMediaId();
        id2.setUserId(1L);
        id2.setSocialMediaId(2L);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        UserSocialMediaId id = new UserSocialMediaId();
        id.setUserId(1L);
        id.setSocialMediaId(2L);

        assertThat(id.getUserId()).isEqualTo(1L);
        assertThat(id.getSocialMediaId()).isEqualTo(2L);
    }

    @Test
    void testEqualsFailsForDifferentObjects() {
        UserSocialMediaId id1 = new UserSocialMediaId();
        id1.setUserId(1L);
        id1.setSocialMediaId(2L);

        UserSocialMediaId id2 = new UserSocialMediaId();
        id2.setUserId(3L);
        id2.setSocialMediaId(4L);

        assertThat(id1).isNotEqualTo(id2);
    }
}
