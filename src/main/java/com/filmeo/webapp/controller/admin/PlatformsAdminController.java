package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.StreamingPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlatformsAdminController {
    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @GetMapping("/admin/platforms")
    public String showPlatformList(
            Model model
    ) {
        return "admin/platform/platforms";
    }
}
