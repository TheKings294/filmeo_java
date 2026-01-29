package com.filmeo.webapp.model.formEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieForm {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    private String resume;

    private String posterURL;

    @NotNull
    private Integer realId;

    @NotNull(message = "At least one genre is required")
    private List<Integer> genresId = new ArrayList<>();

    @NotNull(message = "At least one actor is required")
    private List<Integer> castingId = new ArrayList<>();

    @NotNull(message = "At least one nationality is required")
    private List<Integer> nationalitiesId = new ArrayList<>();

    @Valid
    private List<PlatformMovieForm> platformMoviesId = new ArrayList<>();
}
