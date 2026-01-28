package com.filmeo.webapp.model.dto.platformmovies;

import com.filmeo.webapp.model.dto.movie.MovieLightDTO;
import com.filmeo.webapp.model.dto.platform.PlatformLightDTO;
import com.filmeo.webapp.model.entity.PlatformMovie;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlatformMoviesDTO {
    private MovieLightDTO movie;
    private PlatformLightDTO platform;
    private LocalDate endDate;

    public PlatformMoviesDTO(PlatformMovie platformMovie) {
        this.movie = new MovieLightDTO(platformMovie.getMovie());
        this.platform = new PlatformLightDTO(platformMovie.getPlatform());
        this.endDate = platformMovie.getEndDate();
    }
}
