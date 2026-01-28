package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersAdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String showUserList(
            Model model
    ) {
        return "admin/user/users";
    }
}
