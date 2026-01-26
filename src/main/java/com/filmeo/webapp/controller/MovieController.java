package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.MovieDTO;
import com.filmeo.webapp.model.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public String showMovies(
            Model model

    ) {
        List<MovieDTO> movieDTOS = movieService.selectAll().stream().map(MovieDTO::new).toList();
        model.addAttribute("movies", movieDTOS);
        model.addAttribute("content","public/movies");

        return "base";
    }
}
