package com.filmeo.webapp.model.dto.movie;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.entity.*;
import lombok.Data;

@Data
public class MovieLightDTO {
    private String title;
    private String resume;
    private String posterURL;
    private HumanLightDTO real;

    public MovieLightDTO(Movie movie) {
        this.title = movie.getTitle();
        this.resume = movie.getResume();
        this.posterURL = movie.getPosterURL();
        this.real = new HumanLightDTO(movie.getRealisator());
    }
}
