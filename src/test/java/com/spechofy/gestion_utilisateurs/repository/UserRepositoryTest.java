package com.spechofy.gestion_utilisateurs.repository;

import com.spechofy.gestion_utilisateurs.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use in-memory database
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testExistsByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("test@example.com");
        assertThat(exists).isTrue();
    }
}
