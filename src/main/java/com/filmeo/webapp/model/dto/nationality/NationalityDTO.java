package com.filmeo.webapp.model.dto.nationality;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.entity.Nationality;
import lombok.Data;

import java.util.List;

@Data
public class NationalityDTO {
    private String name;
    private String flagUrl;
    private List<HumanLightDTO> humans;

    public NationalityDTO(Nationality nationality) {
        this.name = nationality.getName();
        this.flagUrl = nationality.getFlagUrl();
        this.humans = nationality.getHumans().stream().map(HumanLightDTO::new).toList();
    }
}
