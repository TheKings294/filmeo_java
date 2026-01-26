package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.SeriDTO;
import com.filmeo.webapp.model.service.SeriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeriController {
    @Autowired
    private SeriService seriService;

    @GetMapping("/series")
    public String showSeries(
            Model model
    ) {
        model.addAttribute("series", seriService.selectAll().stream().map(SeriDTO::new).toList());
        model.addAttribute("content", "public/series");

        return "base";
    }
}
