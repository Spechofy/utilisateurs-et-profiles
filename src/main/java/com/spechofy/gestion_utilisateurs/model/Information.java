package com.spechofy.gestion_utilisateurs.model;

import com.spechofy.gestion_utilisateurs.model.enums.Gender;
import com.spechofy.gestion_utilisateurs.model.enums.Orientation;
import com.spechofy.gestion_utilisateurs.model.enums.Situation;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Situation situation;

    @Enumerated(EnumType.STRING)
    private Orientation orientation;
}


