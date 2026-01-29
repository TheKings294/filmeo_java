package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreForm {
    @NotBlank(message = "The can not be null")
    @Size(min = 5, message = "The name need to be more than 5")
    private String name;

    private String description;
}
