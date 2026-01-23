package com.filmeo.webapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Genre extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
}
