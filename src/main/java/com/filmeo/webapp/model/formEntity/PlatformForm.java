package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatformForm {
    @NotNull
    @Max(40)
    private String name;

    @NotNull
    private String link;

    private String logoUrl;
}
