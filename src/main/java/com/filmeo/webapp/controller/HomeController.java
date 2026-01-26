package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.service.MovieService;
import com.filmeo.webapp.model.service.SeriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriService seriService;

    @GetMapping("/home")
    public String showHome(
            Model model
    ) {
        model.addAttribute("content", "public/home");

        return "base";
    }
}
