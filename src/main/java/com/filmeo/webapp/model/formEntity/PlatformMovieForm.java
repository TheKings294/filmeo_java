package com.filmeo.webapp.model.formEntity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlatformMovieForm {
    private Integer platformId;
    private LocalDate endDate;
}
