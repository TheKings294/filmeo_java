package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.movie.MovieDTO;
import com.filmeo.webapp.model.service.MovieService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

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
        model.addAttribute("content","public/movies");

        return "base";
    }
}
