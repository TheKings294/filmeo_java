package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NationalityForm {
    @NotBlank(message = "The name can not be null")
    @Size(max = 100, message = "The max length is 100")
    private String name;

    private String flagUrl;
}
