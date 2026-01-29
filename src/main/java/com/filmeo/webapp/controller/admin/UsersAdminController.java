package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.user.UserDTO;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.formEntity.UserForm;
import com.filmeo.webapp.model.service.UserService;
import com.filmeo.webapp.service.CountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CountService countService;

    @GetMapping("/admin/users")
    public String showUserList(
            Model model,
            @RequestParam(required = false) Integer pageNumber
    ) {
        if (pageNumber == null) pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<UserDTO> page = userService.selectAll(pageable).map(UserDTO::new);

        model.addAttribute("users", page);
        model.addAttribute("count", countService.getTotalCount());
        model.addAttribute("page", page);

        return "admin/user/users";
    }

    @GetMapping("/admin/users/new")
    public String showUserForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("editMode", false);
        return "admin/user/form";
    }

    @PostMapping("/admin/users/new")
    public String newUser(
            Model model,
            @Valid UserForm userForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("editMode", false);
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
        userForm.setRoles(user.getRoles());

        model.addAttribute("userForm", userForm);
        model.addAttribute("userId", id);
        model.addAttribute("editMode", true);

        return "admin/user/form";
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(
            @PathVariable Integer id,
            @Valid UserForm userForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors() && bindingResult.hasFieldErrors("pseudo") && bindingResult.hasFieldErrors("email")) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("editMode", true);
            return "admin/user/form";
        }

        User user = userService.selectById(id);
        user.setPseudo(userForm.getPseudo());
        user.setEmail(userForm.getEmail());
        user.setRoles(userForm.getRoles());

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