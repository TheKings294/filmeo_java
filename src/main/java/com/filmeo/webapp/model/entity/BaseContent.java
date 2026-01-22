package com.filmeo.webapp.model.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public abstract class BaseContent extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String resume;
}
