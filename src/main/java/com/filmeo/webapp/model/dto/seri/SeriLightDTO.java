package com.filmeo.webapp.model.dto.seri;

import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.type.SeriStatusEnum;
import lombok.Data;


@Data
public class SeriLightDTO {
    private String title;
    private String resume;
    private String posterURL;
    private Integer seasons;
    private Integer episode;
    private SeriStatusEnum staus;

    public SeriLightDTO(Seri seri) {
        this.title = seri.getTitle();
        this.resume = seri.getResume();
        this.posterURL = seri.getPosterURL();
        this.seasons = seri.getSeasons();
        this.episode = seri.getEpisode();
        this.staus = seri.getStaus();
    }
}
