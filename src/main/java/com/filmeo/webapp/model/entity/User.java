package com.filmeo.webapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
@Data
public class User extends BaseEntity {
    @Column(nullable = false, length = 50, unique = true)
    private String pseudo;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<String>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<Review>();

    @ManyToMany
    @JoinTable(
            name = "wish_list_movie",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<Movie> wishListMovie = new ArrayList<Movie>();

    @ManyToMany
    @JoinTable(
            name = "wish_list_seri",
            joinColumns = { @JoinColumn(name = "seri_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<Seri> wishListSeri = new ArrayList<Seri>();
}
