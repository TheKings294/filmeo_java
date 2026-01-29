package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlatformMovieForm {
    @NotNull(message = "Platform is required")
    private Integer platformId;

    @NotNull(message = "End date is required")
    private LocalDate endDate;
}
