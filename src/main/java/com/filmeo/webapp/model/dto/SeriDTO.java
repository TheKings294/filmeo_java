package com.filmeo.webapp.model.dto;

import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.type.SeriStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class SeriDTO {
    private String title;
    private String resume;
    private String posterURL;
    private Integer seasons;
    private Integer episode;
    private SeriStatusEnum staus;
    private List<Genre> genres;
    private List<Human> casting;
    private List<Nationality> nationalities;
    private List<PlatformSeri> platformSeris;

    public SeriDTO(Seri seri) {
        this.title = seri.getTitle();
        this.resume = seri.getResume();
        this.posterURL = seri.getPosterURL();
        this.seasons = seri.getSeasons();
        this.episode = seri.getEpisode();
        this.staus = seri.getStaus();
//        this.genres = seri.getGenres();
//        this.casting = seri.getCasting();
//        this.nationalities = seri.getNationalities();
//        this.platformMovies = seri.getPlatformSeries();
    }
}
