package com.filmeo.webapp.model.dto.movie;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityLightDTO;
import com.filmeo.webapp.model.dto.platformmovies.PlatformMoviesDTO;
import com.filmeo.webapp.model.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {
    private String title;
    private String resume;
    private String posterURL;
    private HumanLightDTO real;
    private List<Genre> genres;
    private List<HumanLightDTO> casting;
    private List<NationalityLightDTO> nationalities;
    private List<PlatformMoviesDTO> platformMovies;

    public MovieDTO(Movie movie) {
        this.title = movie.getTitle();
        this.resume = movie.getResume();
        this.posterURL = movie.getPosterURL();
        this.real = new HumanLightDTO(movie.getRealisator());
        this.genres = movie.getGenres();
        this.casting = movie.getCasting().stream().map(HumanLightDTO::new).toList();
        this.nationalities = movie.getNationalities().stream().map(NationalityLightDTO::new).toList();
        this.platformMovies = movie.getPlatformMovies().stream().map(PlatformMoviesDTO::new).toList();
    }
}
