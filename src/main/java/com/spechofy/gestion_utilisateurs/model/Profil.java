package com.spechofy.gestion_utilisateurs.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profilId;

    private String photo;
    private String biography;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id")
    private Information information;
}

