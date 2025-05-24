package com.spechofy.gestion_utilisateurs.model;

import com.spechofy.gestion_utilisateurs.model.enums.Gender;
import com.spechofy.gestion_utilisateurs.model.enums.Orientation;
import com.spechofy.gestion_utilisateurs.model.enums.Situation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Entity representing additional personal information for a user profile.
 * 
 * This class contains demographic and personal information such as age,
 * gender, relationship status, and sexual orientation.
 * 
 * @author Spechofy Team
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    /**
     * Unique identifier for the information record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's age.
     */
    private Integer age;

    /**
     * User's gender identity.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * User's relationship status.
     */
    @Enumerated(EnumType.STRING)
    private Situation situation;

    /**
     * User's sexual orientation.
     */
    @Enumerated(EnumType.STRING)
    private Orientation orientation;
}
