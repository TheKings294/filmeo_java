package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.type.SeriStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeriForm {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    private String resume;

    private String posterURL;

    @NotNull
    private Integer realiseYear;

    @NotNull(message = "Number of seasons is required")
    @Min(value = 1, message = "There must be at least 1 season")
    private Integer seasons;

    @NotNull(message = "Number of episodes is required")
    @Min(value = 1, message = "There must be at least 1 episode")
    private Integer episode;

    @NotNull(message = "Status is required")
    private SeriStatusEnum status;

    @NotEmpty(message = "At least one genre is required")
    private List<Integer> genresId = new ArrayList<>();

    @NotEmpty(message = "At least one actor is required")
    private List<Integer> castingId = new ArrayList<>();

    @NotEmpty(message = "At least one nationality is required")
    private List<Integer> nationalitiesId = new ArrayList<>();

    @Valid
    private List<PlatformSeriForm> platformSerisId = new ArrayList<>();
}
