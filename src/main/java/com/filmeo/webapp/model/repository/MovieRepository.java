package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
