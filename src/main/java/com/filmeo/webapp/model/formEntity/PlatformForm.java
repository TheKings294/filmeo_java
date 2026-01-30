package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlatformForm {
    @NotBlank(message = "The name can not be null")
    @Size(max = 40, message = "The max length is 40")
    private String name;

    @NotBlank(message = "The link can not be null")
    private String link;

    private String logoUrl;
}
