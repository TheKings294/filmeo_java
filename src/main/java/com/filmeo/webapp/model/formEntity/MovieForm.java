package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.model.entity.Human;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieForm {
    @NotNull
    private String title;

    private String resume;

    private String posterURL;

    @NotNull
    private Integer realId;

    @NotNull
    @Min(1)
    private List<Integer> genresId = new ArrayList<>();

    @NotNull
    @Min(1)
    private List<Integer> castingId = new ArrayList<>();

    @NotNull
    @Min(1)
    private List<Integer> nationalitiesId = new ArrayList<>();

    @NotNull
    private List<PlatformMovieForm> platformMoviesId = new ArrayList<>();
}
