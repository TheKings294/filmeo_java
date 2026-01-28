package com.filmeo.webapp.model.dto.genre;

import com.filmeo.webapp.model.entity.Genre;
import lombok.Data;

@Data
public class GenreDTO {
    private String name;
    private String description;

    public GenreDTO(Genre genre) {
        this.name = genre.getName();
        this.description = genre.getDescription();
    }
}
