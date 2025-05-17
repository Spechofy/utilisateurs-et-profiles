package com.spechofy.gestion_utilisateurs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@SpringBootTest
class GestionUtilisateursApplicationTests {

    @Test
    void contextLoads() {
        // Test if the application context loads successfully
    }

    @Configuration
    static class TestConfig {
        @Bean
        public CommandLineRunner dataLoader() {
            return args -> {};
        }
    }
}
