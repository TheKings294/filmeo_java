package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.SeriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeriesAdminController {
    @Autowired
    private SeriService seriService;

    @GetMapping("/admin/series")
    public String showSeriesList(
            Model model
    ) {
        return "admin/seri/series";
    }
}
