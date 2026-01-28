package com.filmeo.webapp.model.formEntity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlatformSeriForm {
    private Integer platformId;
    private LocalDate endDate;
}
