package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.movie.MovieDTO;
import com.filmeo.webapp.model.dto.seri.SeriDTO;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.service.MovieService;
import com.filmeo.webapp.model.service.ReviewService;
import com.filmeo.webapp.model.service.SeriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriService seriService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String showHome(
            Model model
    ) {
        List<MovieDTO> movies = new ArrayList<>();
        List<SeriDTO> series = new ArrayList<>();
        Map<Integer, Double> avgByMovieId = new HashMap<>();
        Map<Integer, Double> avgBySeriId = new HashMap<>();

        reviewService.getTop10MovieByStar().forEach(movie -> {
            movies.add(new MovieDTO(movie));
            avgByMovieId.put(movie.getId(), movieService.avgRate(movie.getId()));
        });

        reviewService.getTop10SeriByStar().forEach(seri -> {
            series.add(new SeriDTO(seri));
            avgBySeriId.put(seri.getId(), seriService.avgRate(seri.getId()));
        });

        model.addAttribute("avgByMovieId", avgByMovieId);
        model.addAttribute("avgBySeriId", avgBySeriId);
        model.addAttribute("featuredContent", movies.getFirst());
        model.addAttribute("movies", movies);
        model.addAttribute("series", series);
        model.addAttribute("content", "public/home");

        return "base";
    }
}
