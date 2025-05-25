package com.spechofy.gestion_utilisateurs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.spechofy.gestion_utilisateurs.model.Information;
import com.spechofy.gestion_utilisateurs.model.Profil;
import com.spechofy.gestion_utilisateurs.model.SocialMedia;
import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.model.enums.Gender;
import com.spechofy.gestion_utilisateurs.model.enums.Orientation;
import com.spechofy.gestion_utilisateurs.model.enums.Situation;
import com.spechofy.gestion_utilisateurs.repository.InformationRepository;
import com.spechofy.gestion_utilisateurs.repository.ProfilRepository;
import com.spechofy.gestion_utilisateurs.repository.SocialMediaRepository;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import com.spechofy.gestion_utilisateurs.repository.UserSocialMediaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Main application class for the User and Profile Management Service.
 * 
 * This class initializes the Spring Boot application and provides
 * configuration for data loading during development.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@SpringBootApplication
public class GestionUtilisateursApplication {

	/**
	 * Main method that starts the application.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(final String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(GestionUtilisateursApplication.class, args);
	}

	/**
	 * Creates a CommandLineRunner bean that loads initial data into the database.
	 * 
	 * @param userRepository Repository for User entities
	 * @param profilRepository Repository for Profile entities
	 * @param informationRepository Repository for Information entities
	 * @param socialMediaRepository Repository for SocialMedia entities
	 * @param userSocialMediaRepository Repository for UserSocialMedia entities
	 * @return A CommandLineRunner that populates the database with sample data
	 */
	@Bean
	public CommandLineRunner dataLoader(final UserRepository userRepository, 
			final ProfilRepository profilRepository, 
			final InformationRepository informationRepository, 
			final SocialMediaRepository socialMediaRepository, 
			final UserSocialMediaRepository userSocialMediaRepository) {
		return args -> {
			List<SocialMedia> platforms = List.of(
				SocialMedia.builder().plateformeName("Spotify").build(),
				SocialMedia.builder().plateformeName("Apple Music").build(),
				SocialMedia.builder().plateformeName("YouTube Music").build(),
				SocialMedia.builder().plateformeName("Amazon Music").build(),
				SocialMedia.builder().plateformeName("Deezer").build()
			);
			socialMediaRepository.saveAll(platforms);

			for (int i = 1; i <= 5; i++) {
				// Assign different locales to sample users
				String locale;
				switch (i % 5) {
					case 0: locale = "en"; break;
					case 1: locale = "en-US"; break;
					case 2: locale = "fr-FR"; break;
					case 3: locale = "es-ES"; break;
					default: locale = "pt-BR"; break;
				}

				// Generate sample coordinates based on user index
				Double latitude = 40.0 + (i * 0.01);
				Double longitude = -74.0 + (i * 0.01);

				User user = User.builder()
					.firstName("User" + i)
					.lastName("LastName" + i)
					.pseudo("user" + i)
					.birthDate(LocalDate.of(1990 + i, i, i))
					.email("user" + i + "@example.com")
					.phone("12345678" + i)
					.locale(locale)
					.latitude(latitude)
					.longitude(longitude)
					.locationUpdatedAt(LocalDateTime.now())
					.build();

				Profil profil = Profil.builder()
					.photo("profile" + i + ".jpg")
					.biography("This is User" + i + "'s biography.")
					.user(user)
					.build();

				Information info = Information.builder()
					.age(30 + i)
					.gender(i % 2 == 0 ? Gender.MAN : Gender.WOMAN)
					.situation(i % 2 == 0 ? Situation.SINGLE : Situation.MARRIED)
					.orientation(i % 2 == 0 ? Orientation.HETEROSEXUAL : Orientation.BISEXUAL)
					.build();

				profil.setInformation(info);
				user.setProfil(profil);

				userRepository.save(user);
			}
		};
	}
}
