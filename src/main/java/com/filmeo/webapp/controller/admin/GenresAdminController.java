package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenresAdminController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/admin/genres")
    public String showGenreList(
            Model model
    ) {
        return "admin/genre/genres";
    }
}
