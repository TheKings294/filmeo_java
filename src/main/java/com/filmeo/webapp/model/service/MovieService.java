package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Movie> selectAll() {
        return this.movieRepository.findAll();
    }

    public Movie selectById(Integer id) throws BusinessException {
        return this.movieRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Movie"
                )
        );
    }

    public Movie insert(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public Movie update(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public boolean delete(Movie movie) {
        if (!this.movieRepository.existsById(movie.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("movie %d", movie.getId())
        );

        this.movieRepository.delete(movie);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.movieRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("movie %d", id)
        );

        this.movieRepository.deleteById(id);
        return true;
    }

    public List<Movie> getMoviesByActor(Integer actorId) {
        return movieRepository.findMoviesByActorId(actorId);
    }

    public Double avgRate(Integer id) {
        return entityManager.createQuery(
                        """
                        SELECT AVG(r.rate)
                        FROM Review r
                        WHERE r.movie.id = :movieId
                        """,
                        Double.class
                )
                .setParameter("movieId", id)
                .getSingleResult();
    }
}
