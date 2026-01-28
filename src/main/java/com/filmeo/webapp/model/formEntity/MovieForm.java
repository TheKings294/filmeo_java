package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.model.entity.Genre;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.entity.Nationality;
import com.filmeo.webapp.model.entity.PlatformMovie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MovieForm {
    @NotNull
    private String title;

    private String resume;

    private String posterURL;

    @NotNull
    private Human real;

    @NotNull
    @Min(1)
    private List<Genre> genres;

    @NotNull
    @Min(1)
    private List<Human> casting;

    @NotNull
    @Min(1)
    private List<Nationality> nationalities;

    @NotNull
    private List<PlatformMovie> platformMovies;
}
