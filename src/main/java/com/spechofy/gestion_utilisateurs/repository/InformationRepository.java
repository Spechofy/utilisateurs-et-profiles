package com.spechofy.gestion_utilisateurs.repository;

import com.spechofy.gestion_utilisateurs.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
    // Additional query methods can be defined here if needed
}
