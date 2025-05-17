package com.spechofy.gestion_utilisateurs.repository;

import com.spechofy.gestion_utilisateurs.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Optional<Profil> findByUser_UserId(Long userId);
}
