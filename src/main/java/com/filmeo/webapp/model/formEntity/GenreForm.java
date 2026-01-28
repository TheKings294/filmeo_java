package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenreForm {
    @NotNull
    @Max(50)
    private String name;

    private String description;
}
