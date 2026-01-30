package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.PlatformMovie;
import com.filmeo.webapp.model.formEntity.PlatformMovieForm;
import com.filmeo.webapp.model.repository.MovieRepository;
import com.filmeo.webapp.model.repository.PlatformMovieRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @Autowired
    private EntityManager entityManager;

    public List<Movie> selectAll() {
        return this.movieRepository.findAll();
    }

    public Page<Movie> selectAll(Pageable pageable) {
        return this.movieRepository.findAll(pageable);
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

    public void deletePlatformMovie(Movie movie) {
        movie.getPlatformMovies().clear();
    }

    public void addPlatformMovie(Movie movie, PlatformMovie pm) {
        movie.getPlatformMovies().add(pm);
    }

    @Transactional
    public void updatePlatformMovies(Movie movie, List<PlatformMovieForm> platformForms) {
        // Ensure managed entity
        Movie managedMovie = movieRepository.findById(movie.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Delete all old platforms
        managedMovie.getPlatformMovies().clear();

        // Add new platforms
        for (PlatformMovieForm pmForm : platformForms) {
            PlatformMovie pm = new PlatformMovie();
            pm.setMovie(managedMovie); // MUST set parent
            pm.setPlatform(streamingPlatformService.selectById(pmForm.getPlatformId()));
            pm.setEndDate(pmForm.getEndDate());

            managedMovie.getPlatformMovies().add(pm); // Hibernate will persist automatically
        }

        // Optional: save parent, Hibernate flushes automatically due to @Transactional
        movieRepository.save(managedMovie);
    }

    public List<Movie> searchMovies(String keyWord) {
        return movieRepository.searchByKeyword(keyWord);
    }
}
