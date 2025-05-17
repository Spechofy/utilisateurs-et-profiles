package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Set;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialMediaId;

    private String plateformeName;

    @OneToMany(mappedBy = "socialMedia", cascade = CascadeType.ALL)
    private Set<UserSocialMedia> userLinks;
}
