package com.filmeo.webapp.model.dto;

import com.filmeo.webapp.model.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {
    private String title;
    private String resume;
    private String posterURL;
    private Human real;
    private List<Genre> genres;
    private List<Human> casting;
    private List<Nationality> nationalities;
    private List<PlatformMovie> platformMovies;

    public MovieDTO(Movie movie) {
        this.title = movie.getTitle();
        this.resume = movie.getResume();
        this.posterURL = movie.getPosterURL();
//        this.real = movie.getRealisator();
//        this.genres = movie.getGenres();
//        this.casting = movie.getCasting();
//        this.nationalities = movie.getNationalities();
//        this.platformMovies = movie.getPlatformMovies();
    }
}
