package com.filmeo.webapp.model.dto.seri;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityLightDTO;
import com.filmeo.webapp.model.dto.platformseries.PlatformSeriesDTO;
import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.type.SeriStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class SeriDTO {
    private Integer id;
    private String title;
    private String resume;
    private String poster;
    private Integer realiseYear;
    private Integer seasons;
    private Integer episode;
    private SeriStatusEnum staus;
    private List<Genre> genres;
    private List<HumanLightDTO> casting;
    private List<NationalityLightDTO> nationalities;
    private List<PlatformSeriesDTO> platformSeris;
    private String type = "SERI";

    public SeriDTO(Seri seri) {
        this.id = seri.getId();
        this.title = seri.getTitle();
        this.resume = seri.getResume();
        this.poster = seri.getPosterURL();
        this.realiseYear = seri.getRealiseYear();
        this.seasons = seri.getSeasons();
        this.episode = seri.getEpisode();
        this.staus = seri.getStaus();
        this.genres = seri.getGenres();
        this.casting = seri.getCasting().stream().map(HumanLightDTO::new).toList();
        this.nationalities = seri.getNationalities().stream().map(NationalityLightDTO::new).toList();
        this.platformSeris = seri.getPlatformSeris().stream().map(PlatformSeriesDTO::new).toList();
    }
}
