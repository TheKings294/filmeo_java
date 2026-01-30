package com.filmeo.webapp.model.dto.nationality;

import com.filmeo.webapp.model.entity.Nationality;
import lombok.Data;

@Data
public class NationalityLightDTO {
    private String name;
    private String flagUrl;

    public NationalityLightDTO(Nationality nationality) {
        this.name = nationality.getName();
        this.flagUrl = nationality.getFlagUrl();
    }
}
