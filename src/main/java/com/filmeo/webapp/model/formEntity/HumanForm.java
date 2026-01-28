package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.model.entity.Nationality;
import com.filmeo.webapp.type.GenderEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HumanForm {
    @Max(100)
    private String lastName;

    @NotNull
    @Max(100)
    private String firstName;

    private String profilePicture;

    @NotNull
    private LocalDate birthDate;

    private LocalDate deathDate;

    @NotNull
    private GenderEnum gender;

    private Nationality nationality;
}
