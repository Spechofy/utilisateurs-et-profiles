package com.spechofy.gestion_utilisateurs.repository;

import com.spechofy.gestion_utilisateurs.model.Profil;
import com.spechofy.gestion_utilisateurs.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use in-memory database
@ActiveProfiles("test") // Use a test profile if needed
class ProfilRepositoryTest {

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUser_UserId() {
        User user = new User();
        user.setEmail("test@example.com");
        userRepository.save(user);

        Profil profil = new Profil();
        profil.setUser(user);
        profilRepository.save(profil);

        Optional<Profil> foundProfil = profilRepository.findByUser_UserId(user.getUserId());
        assertThat(foundProfil).isPresent();
    }
}
