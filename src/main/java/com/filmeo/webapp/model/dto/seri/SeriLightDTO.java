package com.filmeo.webapp.model.dto.seri;

import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.type.SeriStatusEnum;
import lombok.Data;


@Data
public class SeriLightDTO {
    private String title;
    private String resume;
    private String poster;
    private Integer seasons;
    private Integer episode;
    private SeriStatusEnum staus;
    private String type = "SERI";

    public SeriLightDTO(Seri seri) {
        this.title = seri.getTitle();
        this.resume = seri.getResume();
        this.poster = seri.getPosterURL();
        this.seasons = seri.getSeasons();
        this.episode = seri.getEpisode();
        this.staus = seri.getStaus();
    }
}
