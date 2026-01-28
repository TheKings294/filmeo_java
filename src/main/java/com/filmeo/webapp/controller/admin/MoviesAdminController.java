package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoviesAdminController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/admin/movies")
    public String showMoviesList(
            Model model
    ) {
        return "admin/movie/movies";
    }
}

