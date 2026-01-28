package com.filmeo.webapp.model.dto;

import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Seri;
import lombok.Data;

@Data
public class MediaDTO {
    private Integer id;
    private String title;
    private String resume;
    private String poster;
    private String type; // "MOVIE" or "SERIES"

    public static MediaDTO fromMovie(Movie movie) {
        MediaDTO dto = new MediaDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setResume(movie.getResume());
        dto.setPoster(movie.getPosterURL());
        dto.setType("MOVIE");
        return dto;
    }

    public static MediaDTO fromSeries(Seri series) {
        MediaDTO dto = new MediaDTO();
        dto.setId(series.getId());
        dto.setTitle(series.getTitle());
        dto.setResume(series.getResume());
        dto.setPoster(series.getPosterURL());
        dto.setType("SERI");
        return dto;
    }
}
