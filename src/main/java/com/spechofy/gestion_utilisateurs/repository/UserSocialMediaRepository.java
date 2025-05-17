package com.spechofy.gestion_utilisateurs.repository;

import com.spechofy.gestion_utilisateurs.model.UserSocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserSocialMediaRepository extends JpaRepository<UserSocialMedia, Long> {
    Optional<UserSocialMedia> findByUserIdAndSocialMediaId(Long userId, Long socialMediaId);
}
