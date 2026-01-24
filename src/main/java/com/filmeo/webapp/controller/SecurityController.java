package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.formEntity.UserForm;
import com.filmeo.webapp.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        model.addAttribute("content", "security/login");

        return "base";
    }

    @GetMapping("/register")
    public String showRegister(
            Model model
    ) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("content", "security/register");

        return "base";
    }
}
