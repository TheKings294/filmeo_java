package com.filmeo.webapp.model.dto.platform;

import com.filmeo.webapp.model.dto.movie.MovieLightDTO;
import com.filmeo.webapp.model.dto.seri.SeriLightDTO;
import com.filmeo.webapp.model.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class StreamingPlatformDTO {
    private String name;
    private String link;
    private String logoUrl;
    private List<MovieLightDTO> movies;
    private List<SeriLightDTO> series;

    public StreamingPlatformDTO(StreamingPlatform streamingPlatform) {
        this.name = streamingPlatform.getName();
        this.link = streamingPlatform.getLink();
        this.logoUrl = streamingPlatform.getLogoUrl();

        List<Movie> movies1 = streamingPlatform.getMovies().stream().map(PlatformMovie::getMovie).toList();
        this.movies = movies1.stream().map(MovieLightDTO::new).toList();
        List<Seri> series1 = streamingPlatform.getSeries().stream().map(PlatformSeri::getSeri).toList();
        this.series = series1.stream().map(SeriLightDTO::new).toList();
    }
}
