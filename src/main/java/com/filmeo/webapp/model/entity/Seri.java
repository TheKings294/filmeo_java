package com.filmeo.webapp.model.entity;

import com.filmeo.webapp.type.SeriStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Seri extends BaseContent {
    @Column(nullable = false)
    private Integer seasons;

    @Column(nullable = false)
    private Integer episode;

    @Column(nullable = false)
    private SeriStatusEnum staus;

    @ManyToMany
    @JoinTable(
            name = "genre_seri",
            joinColumns = { @JoinColumn(name = "genre_id") },
            inverseJoinColumns = { @JoinColumn(name = "seri_id") }
    )
    private List<Genre> genres = new ArrayList<Genre>();

    @ManyToMany
    @JoinTable(
            name = "casting_seri",
            joinColumns = { @JoinColumn(name = "actor_id") },
            inverseJoinColumns = { @JoinColumn(name = "seri_id") }
    )
    private List<Human> casting = new ArrayList<Human>();

    @ManyToMany
    @JoinTable(
            name = "nationality_seri",
            joinColumns = { @JoinColumn(name = "nationality_id") },
            inverseJoinColumns = { @JoinColumn(name = "seri_id") }
    )
    private List<Nationality> nationalities = new ArrayList<Nationality>();

    @OneToMany(mappedBy = "seri")
    private List<PlatformSeri> platformSeris = new ArrayList<PlatformSeri>();

    @OneToMany(mappedBy = "seri")
    private List<Review> reviews = new ArrayList<>();
}
