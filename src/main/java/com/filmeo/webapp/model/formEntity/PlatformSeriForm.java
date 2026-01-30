package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PlatformSeriForm {
    @NotNull(message = "Platform is required")
    private Integer platformId;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
