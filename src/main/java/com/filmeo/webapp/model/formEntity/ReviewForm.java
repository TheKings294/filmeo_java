package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewForm {
    @NotNull
    private String type;

    @NotNull
    private String comment;

    @NotNull
    private Integer rate;
}
