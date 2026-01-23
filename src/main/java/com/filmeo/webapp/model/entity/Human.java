package com.filmeo.webapp.model.entity;

import com.filmeo.webapp.type.GenderEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Human extends BaseEntity {
    @Column(nullable = true, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = true)
    private LocalDate deathDate;

    @Column(nullable = false)
    private GenderEnum gender;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Nationality nationality;

    @OneToMany
    private List<Movie> realisation = new ArrayList<Movie>();
}
