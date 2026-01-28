package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NationalitiesAdminController {
    @Autowired
    private NationalityService nationalityService;

    @GetMapping("/admin/nationalities")
    public String showNationalitiesList(
            Model model
    ) {
        return "admin/nationality/nationalities";
    }
}
