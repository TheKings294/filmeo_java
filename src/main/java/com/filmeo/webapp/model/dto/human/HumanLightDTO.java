package com.filmeo.webapp.model.dto.human;

import com.filmeo.webapp.model.dto.nationality.NationalityLightDTO;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.type.GenderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HumanLightDTO {
    private String lastName;
    private String firstName;
    private String profilePicture;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private GenderEnum gender;
    private NationalityLightDTO nationality;

    public HumanLightDTO(Human human) {
        this.lastName = human.getLastName();
        this.firstName = human.getFirstName();
        this.profilePicture = human.getProfilePicture();
        this.birthDate = human.getBirthDate();
        this.deathDate = human.getDeathDate();
        this.gender = human.getGender();
        this.nationality = new NationalityLightDTO(human.getNationality());
    }
}
