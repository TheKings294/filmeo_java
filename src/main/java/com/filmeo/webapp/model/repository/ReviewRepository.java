package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId")
    List<Review> findReviewsByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT r FROM Review r WHERE r.seri.id = :seriId")
    List<Review> findReviewsBySeriId(@Param("seriId") Integer seriId);
}
