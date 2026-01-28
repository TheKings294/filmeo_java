package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Review;
import com.filmeo.webapp.model.entity.Seri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId")
    List<Review> findReviewsByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT r FROM Review r WHERE r.seri.id = :seriId")
    List<Review> findReviewsBySeriId(@Param("seriId") Integer seriId);

    @Query("SELECT m FROM Movie m " +
            "LEFT JOIN m.reviews r " +
            "GROUP BY m.id " +
            "ORDER BY AVG(r.rate) DESC, COUNT(r) DESC LIMIT 10")
    List<Movie> findTop10MovieByRating();

    // Alternative: Top 10 movies by number of reviews
    @Query("SELECT m FROM Movie m " +
            "LEFT JOIN m.reviews r " +
            "GROUP BY m.id " +
            "ORDER BY COUNT(r) DESC limit 10")
    List<Movie> findTop10MovieByReviewCount();

    @Query("SELECT s FROM Seri s " +
            "LEFT JOIN s.reviews r " +
            "GROUP BY s.id " +
            "ORDER BY AVG(r.rate) DESC, COUNT(r) DESC limit 10")
    List<Seri> findTop10SeriByRating();

    // Alternative: Top 10 movies by number of reviews
    @Query("SELECT s FROM Seri s " +
            "LEFT JOIN s.reviews r " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(r) DESC limit 10")
    List<Seri> findTop10SeriByReviewCount();
}
