package com.filmeo.webapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review extends BaseEntity {
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer rate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Seri seri;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Movie movie;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Human actor;
}
