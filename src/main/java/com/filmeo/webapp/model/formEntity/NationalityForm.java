package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NationalityForm {
    @NotNull
    @Max(100)
    private String name;

    private String flagUrl;
}
