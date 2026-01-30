package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.entity.ConnectedUser;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.formEntity.UserForm;
import com.filmeo.webapp.model.service.CustomUserDetailsService;
import com.filmeo.webapp.model.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public String showUserForm(
            Model model,
            @AuthenticationPrincipal ConnectedUser userC
    ) {
        User user = userService.selectById(userC.getUser().getId());
        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setPseudo(user.getPseudo());

        model.addAttribute("userForm", userForm);

        return "public/user/form";
    }

    @PostMapping("/me")
    public String updateUser(
            Model model,
            @Valid UserForm userForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal ConnectedUser userC
    ) {
        if (bindingResult.hasErrors() && bindingResult.hasFieldErrors("pseudo") && bindingResult.hasFieldErrors("email")) {
            model.addAttribute("userForm", userForm);

            return "public/user/form";
        }

        User user = userService.selectById(userC.getUser().getId());

        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("error", "Password and is confirmation must be the same");
            model.addAttribute("userForm", userForm);
            return "public/user/form";
        }

        if (this.userDetailsService.existsByUsername(userForm.getEmail()) && (!user.getEmail().equals(userForm.getEmail()))) {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("userForm", userForm);
            return "public/user/form";
        }

        user.setEmail(userForm.getEmail());
        user.setPseudo(userForm.getPseudo());

        if (!userForm.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }

        userService.update(user);

        return "redirect:/";
    }
}
