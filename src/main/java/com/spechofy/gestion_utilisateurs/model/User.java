package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String pseudo;
    private LocalDate birthDate;
    private String email;
    private String phone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profil profil;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserSocialMedia> socialMediaLinks;
}

