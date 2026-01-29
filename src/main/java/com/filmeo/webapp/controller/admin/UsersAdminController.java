package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.user.UserDTO;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.formEntity.UserForm;
import com.filmeo.webapp.model.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.selectAll().stream().map(UserDTO::new).toList());
        return "admin/user/users";
    }

    @GetMapping("/admin/users/new")
    public String showUserForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "admin/user/form";
    }

    @PostMapping("/admin/users/new")
    public String newUser(
            @Valid UserForm userForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/user/form";
        }

        User user = new User();
        user.setPseudo(userForm.getPseudo());
        user.setEmail(userForm.getEmail());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));

        userService.insert(user);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/update/{id}")
    public String showUpdateForm(
            @PathVariable Integer id,
            Model model
    ) {
        User user = userService.selectById(id);

        UserForm userForm = new UserForm();
        userForm.setPseudo(user.getPseudo());
        userForm.setEmail(user.getEmail());

        model.addAttribute("userForm", userForm);
        model.addAttribute("userId", id);

        return "admin/user/form";
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(
            @PathVariable Integer id,
            @Valid UserForm userForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/user/form";
        }

        User user = userService.selectById(id);
        user.setPseudo(userForm.getPseudo());
        user.setEmail(userForm.getEmail());

        if (userForm.getPassword() != null && !userForm.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }

        userService.update(user);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}