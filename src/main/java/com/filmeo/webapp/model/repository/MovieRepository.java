package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.casting c WHERE c.id = :actorId")
    List<Movie> findMoviesByActorId(@Param("actorId") Integer actorId);
}
