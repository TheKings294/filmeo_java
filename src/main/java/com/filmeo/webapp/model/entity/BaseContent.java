package com.filmeo.webapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseContent extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String resume;

    private String posterURL;

    @NotNull
    private Integer realiseYear;
}
