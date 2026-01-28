package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HumansAdminController {
    @Autowired
    private HumanService humanService;

    @GetMapping("/admin/humans")
    public String showHumanList(
            Model model
    ) {
        return "admin/human/humans";
    }
}
