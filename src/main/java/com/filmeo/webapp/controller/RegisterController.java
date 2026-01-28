package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.formEntity.UserForm;
import com.filmeo.webapp.model.service.CustomUserDetailsService;
import com.filmeo.webapp.model.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String processRegister(
            Model model,
            @Valid
            UserForm userForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("content", "security/register");
            System.out.println(bindingResult.toString());
            return "base";
        }

        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("error", "Password and is confirmation must be the same");
            model.addAttribute("userForm", userForm);
            model.addAttribute("content", "security/register");
            return "base";
        }

        if (this.userDetailsService.existsByUsername(userForm.getEmail())) {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("userForm", userForm);
            model.addAttribute("content", "security/register");
            return "base";
        }

        User user = new User();
        user.setPseudo(userForm.getPseudo());
        user.setEmail(userForm.getEmail());
        user.setPassword(this.passwordEncoder.encode(userForm.getPassword()));
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());

        this.userService.insert(user);

        return "redirect:/login?registered=true";
    }
}
