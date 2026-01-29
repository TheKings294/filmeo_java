package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.nationality.NationalityDTO;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.formEntity.HumanForm;
import com.filmeo.webapp.model.service.HumanService;
import com.filmeo.webapp.model.service.NationalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HumansAdminController {
    @Autowired
    private HumanService humanService;

    @Autowired
    private NationalityService nationalityService;

    @GetMapping("/admin/humans")
    public String showHumanList(
            Model model
    ) {
        return "admin/human/humans";
    }

    @GetMapping("/admin/humans/new")
    public String showForm(
            Model model
    ) {
        model.addAttribute("humanForm", new HumanForm());
        model.addAttribute("nationalities",
                nationalityService.selectAll().stream().map(NationalityDTO::new).toList());

        return "admin/human/form";
    }

    @PostMapping("/admin/humans/new")
    public String newGenre(
            Model model,
            @Valid HumanForm humanForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("humanForm", new HumanForm());
            model.addAttribute("nationalities",
                    nationalityService.selectAll().stream().map(NationalityDTO::new).toList());

            return "admin/human/form";
        }

        Human human = new Human();
        human.setProfilePicture(humanForm.getProfilePicture());
        human.setNationality(nationalityService.selectById(humanForm.getNationalityId()));
        human.setGender(humanForm.getGender());
        human.setDeathDate(humanForm.getDeathDate());
        human.setBirthDate(humanForm.getBirthDate());
        human.setLastName(humanForm.getLastName());

        humanService.insert(human);

        return "redirect:/admin/humans";
    }

    @GetMapping("/admin/humans/update/{id}")
    public String showForm(
            Model model,
            @PathVariable Integer id
    ) {
        Human human = humanService.selectById(id);
        HumanForm humanForm = new HumanForm();
        humanForm.setProfilePicture(human.getProfilePicture());
        humanForm.setNationalityId(human.getNationality().getId());
        humanForm.setGender(human.getGender());
        humanForm.setDeathDate(human.getDeathDate());
        humanForm.setBirthDate(human.getBirthDate());
        humanForm.setLastName(human.getLastName());

        model.addAttribute("human", humanForm);
        model.addAttribute("nationalities",
                nationalityService.selectAll().stream().map(NationalityDTO::new).toList());

        return "admin/human/form ";
    }

    @PostMapping("/admin/humans/update/{id}")
    public String updateGenre(
            Model model,
            @Valid HumanForm humanForm,
            @PathVariable Integer id,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("human", humanForm);
            model.addAttribute("nationalities",
                    nationalityService.selectAll().stream().map(NationalityDTO::new).toList());
            return "admin/human/form";
        }

        Human updateHuman = humanService.selectById(id);
        updateHuman.setLastName(humanForm.getLastName());
        updateHuman.setGender(humanForm.getGender());
        updateHuman.setBirthDate(humanForm.getBirthDate());
        updateHuman.setProfilePicture(humanForm.getProfilePicture());
        updateHuman.setNationality(nationalityService.selectById(humanForm.getNationalityId()));
        updateHuman.setDeathDate(humanForm.getDeathDate());
        updateHuman.setFirstName(humanForm.getFirstName());

        humanService.update(updateHuman);

        return "redirect:/admin/humans";
    }

    @PostMapping("/admin/humans/delete/{id}")
    public String deleteGenre(
            Model model,
            @PathVariable Integer id
    ) {
        humanService.delete(id);

        return "redirect:/admin/humans";
    }
}
