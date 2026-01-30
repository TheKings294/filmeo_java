package com.filmeo.webapp.model.dto.movie;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.entity.*;
import lombok.Data;

@Data
public class MovieLightDTO {
    private String title;
    private String resume;
    private String poster;
    private Integer realiseYear;
    private HumanLightDTO real;
    private String type = "MOVIE";

    public MovieLightDTO(Movie movie) {
        this.title = movie.getTitle();
        this.resume = movie.getResume();
        this.poster = movie.getPosterURL();
        this.realiseYear = movie.getRealiseYear();
        this.real = new HumanLightDTO(movie.getRealisator());
    }
}
