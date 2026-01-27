package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.genre.GenreDTO;
import com.filmeo.webapp.model.dto.human.HumanDTO;
import com.filmeo.webapp.model.dto.movie.MovieDTO;
import com.filmeo.webapp.model.dto.platformmovies.PlatformMoviesDTO;
import com.filmeo.webapp.model.dto.review.ReviewDTO;
import com.filmeo.webapp.model.entity.ConnectedUser;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.service.MovieService;
import com.filmeo.webapp.model.service.ReviewService;
import com.filmeo.webapp.model.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/movies")
    public String showMovies(
            Model model

    ) {
        List<MovieDTO> movieDTOS = movieService.selectAll().stream().map(MovieDTO::new).toList();
        List<Object[]> results = entityManager.createQuery(
                """
                   SELECT r.movie.id, AVG(r.rate)
                   FROM Review r
                   GROUP BY r.movie.id
                   """,
                Object[].class
        ).getResultList();
        Map<Integer, Double> avgByMovieId = results.stream()
                        .collect(
                                Collectors.toMap(
                                        r -> (Integer) r[0],
                                        r -> (Double) r[1]
                                )
                        );

        model.addAttribute("movies", movieDTOS);
        model.addAttribute("avgByMovieId", avgByMovieId);
        model.addAttribute("content","public/movie/movies");

        return "base";
    }

    @GetMapping("/movies/{id}")
    public String showOneMovie(
            Model model,
            @PathVariable Integer id,
            @AuthenticationPrincipal ConnectedUser connectedUser
    ) {
        Double avgRate = entityManager.createQuery(
                        """
                        SELECT AVG(r.rate)
                        FROM Review r
                        WHERE r.movie.id = :movieId
                        """,
                        Double.class
                )
                .setParameter("movieId", id)
                .getSingleResult();

        Movie movie = movieService.selectById(id);

        boolean isInWatchlist = false;
        if (connectedUser != null) {
            isInWatchlist = userService.mediaIsInWatchList(connectedUser.getUser().getId(), movie);
        }

        List<Human> directors = new ArrayList<>();
        directors.add(movie.getRealisator());

        model.addAttribute("averageRating", avgRate != null ? avgRate : 0.0);
        model.addAttribute("media", new MovieDTO(movie));
        model.addAttribute("genres", movie.getGenres().stream().map(GenreDTO::new).toList());
        model.addAttribute("actors", movie.getCasting().stream().map(HumanDTO::new).toList());
        model.addAttribute("comments", reviewService.getMovieReviews(movie.getId()).stream().map(ReviewDTO::new).toList());
        model.addAttribute("platforms", movie.getPlatformMovies().stream().map(PlatformMoviesDTO::new).toList());
        model.addAttribute("directors", directors.stream().map(HumanDTO::new).toList());
        model.addAttribute("inWatchlist", isInWatchlist);
        model.addAttribute("content", "public/movie/movie-description");

        return "base";
    }
}
