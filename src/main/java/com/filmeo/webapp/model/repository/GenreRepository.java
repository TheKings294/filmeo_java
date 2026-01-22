package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
