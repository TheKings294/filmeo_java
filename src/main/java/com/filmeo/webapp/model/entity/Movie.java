package com.filmeo.webapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie extends BaseContent {
    @ManyToOne
    private Human realisator;

    @ManyToMany
    @JoinTable(
            name = "genre_movie",
            joinColumns = { @JoinColumn(name = "genre_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Genre> genres = new ArrayList<Genre>();

    @ManyToMany
    @JoinTable(
            name = "casting_movie",
            joinColumns = { @JoinColumn(name = "actor_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Human> casting = new ArrayList<Human>();

    @ManyToMany
    @JoinTable(
            name = "nationality_movie",
            joinColumns = { @JoinColumn(name = "nationality_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Nationality> nationalities = new ArrayList<Nationality>();

    @OneToMany(mappedBy = "movie")
    private List<PlatformMovie> platformMovies = new ArrayList<PlatformMovie>();
}
