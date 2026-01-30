package com.filmeo.webapp.model.dto.human;

import com.filmeo.webapp.model.dto.movie.MovieLightDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityLightDTO;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.type.GenderEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HumanDTO {
    private Integer id;
    private String lastName;
    private String firstName;
    private String profilePicture;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private GenderEnum gender;
    private NationalityLightDTO nationality;
    private List<MovieLightDTO> realisation;

    public HumanDTO(Human human) {
        this.id = human.getId();
        this.lastName = human.getLastName();
        this.firstName = human.getFirstName();
        this.profilePicture = human.getProfilePicture();
        this.birthDate = human.getBirthDate();
        this.deathDate = human.getDeathDate();
        this.gender = human.getGender();
        this.nationality = new NationalityLightDTO(human.getNationality());
        this.realisation = human.getRealisation().stream().map(MovieLightDTO::new).toList();
    }
}
