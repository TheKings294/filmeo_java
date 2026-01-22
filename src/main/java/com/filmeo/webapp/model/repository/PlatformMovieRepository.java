package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.PlatformMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformMovieRepository extends JpaRepository<PlatformMovie, Integer> {
}
