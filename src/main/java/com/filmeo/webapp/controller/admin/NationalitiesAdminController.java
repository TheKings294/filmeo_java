package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.nationality.NationalityDTO;
import com.filmeo.webapp.model.entity.Nationality;
import com.filmeo.webapp.model.formEntity.NationalityForm;
import com.filmeo.webapp.model.service.NationalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NationalitiesAdminController {
    @Autowired
    private NationalityService nationalityService;

    @GetMapping("/admin/nationalities")
    public String showNationalityList(
            Model model,
            @RequestParam(required = false) Integer pageNumber
    ) {
        if (pageNumber == null) pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<NationalityDTO> page = nationalityService.selectAll(pageable).map(NationalityDTO::new);

        model.addAttribute("nationalities", page);
        return "admin/nationality/nationalities";
    }

    @GetMapping("/admin/nationalities/new")
    public String showForm(Model model) {
        model.addAttribute("nationalityForm", new NationalityForm());
        return "admin/nationality/form";
    }

    @PostMapping("/admin/nationalities/new")
    public String newNationality(
            @Valid NationalityForm nationalityForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/nationality/form";
        }

        Nationality nationality = new Nationality();
        nationality.setName(nationalityForm.getName());
        nationality.setFlagUrl(nationalityForm.getFlagUrl());

        nationalityService.insert(nationality);

        return "redirect:/admin/nationalities";
    }

    @GetMapping("/admin/nationalities/update/{id}")
    public String showUpdateForm(
            @PathVariable Integer id,
            Model model
    ) {
        Nationality nationality = nationalityService.selectById(id);

        NationalityForm nationalityForm = new NationalityForm();
        nationalityForm.setName(nationality.getName());
        nationalityForm.setFlagUrl(nationality.getFlagUrl());

        model.addAttribute("nationalityForm", nationalityForm);
        model.addAttribute("nationalityId", id);

        return "admin/nationality/form";
    }

    @PostMapping("/admin/nationalities/update/{id}")
    public String updateNationality(
            @PathVariable Integer id,
            @Valid NationalityForm nationalityForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/nationality/form";
        }

        Nationality nationality = nationalityService.selectById(id);
        nationality.setName(nationalityForm.getName());
        nationality.setFlagUrl(nationalityForm.getFlagUrl());

        nationalityService.update(nationality);

        return "redirect:/admin/nationalities";
    }

    @PostMapping("/admin/nationalities/delete/{id}")
    public String deleteNationality(@PathVariable Integer id) {
        nationalityService.delete(id);
        return "redirect:/admin/nationalities";
    }
}
