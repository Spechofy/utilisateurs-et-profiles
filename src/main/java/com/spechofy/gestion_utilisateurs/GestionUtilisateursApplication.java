package com.spechofy.gestion_utilisateurs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.spechofy.gestion_utilisateurs.model.*;
import com.spechofy.gestion_utilisateurs.model.enums.*;
import com.spechofy.gestion_utilisateurs.repository.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class GestionUtilisateursApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(GestionUtilisateursApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepository, ProfilRepository profilRepository, InformationRepository informationRepository, SocialMediaRepository socialMediaRepository, UserSocialMediaRepository userSocialMediaRepository) {
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
				User user = User.builder()
					.firstName("User" + i)
					.lastName("LastName" + i)
					.pseudo("user" + i)
					.birthDate(LocalDate.of(1990 + i, i, i))
					.email("user" + i + "@example.com")
					.phone("12345678" + i)
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
