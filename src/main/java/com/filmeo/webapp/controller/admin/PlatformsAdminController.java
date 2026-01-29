package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.platform.StreamingPlatformDTO;
import com.filmeo.webapp.model.entity.StreamingPlatform;
import com.filmeo.webapp.model.formEntity.PlatformForm;
import com.filmeo.webapp.model.service.StreamingPlatformService;
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
public class PlatformsAdminController {
    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @GetMapping("/admin/platforms")
    public String showPlatformList(
            Model model,
            @RequestParam(required = false) Integer pageNumber
    ) {
        if (pageNumber == null) pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<StreamingPlatformDTO> page = streamingPlatformService.selectAll(pageable).map(StreamingPlatformDTO::new);

        model.addAttribute("platforms", page);
        return "admin/platform/platforms";
    }

    @GetMapping("/admin/platforms/new")
    public String showForm(Model model) {
        model.addAttribute("platformForm", new PlatformForm());
        return "admin/platform/form";
    }

    @PostMapping("/admin/platforms/new")
    public String newPlatform(
            @Valid PlatformForm platformForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/platform/form";
        }

        StreamingPlatform platform = new StreamingPlatform();
        platform.setName(platformForm.getName());
        platform.setLink(platformForm.getLink());
        platform.setLogoUrl(platformForm.getLogoUrl());

        streamingPlatformService.insert(platform);

        return "redirect:/admin/platforms";
    }

    @GetMapping("/admin/platforms/update/{id}")
    public String showUpdateForm(
            @PathVariable Integer id,
            Model model
    ) {
        StreamingPlatform platform = streamingPlatformService.selectById(id);

        PlatformForm platformForm = new PlatformForm();
        platformForm.setName(platform.getName());
        platformForm.setLink(platform.getLink());
        platformForm.setLogoUrl(platform.getLogoUrl());

        model.addAttribute("platformForm", platformForm);
        model.addAttribute("platformId", id);

        return "admin/platform/form";
    }

    @PostMapping("/admin/platforms/update/{id}")
    public String updatePlatform(
            @PathVariable Integer id,
            @Valid PlatformForm platformForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/platform/form";
        }

        StreamingPlatform platform = streamingPlatformService.selectById(id);
        platform.setName(platformForm.getName());
        platform.setLink(platformForm.getLink());
        platform.setLogoUrl(platformForm.getLogoUrl());

        streamingPlatformService.update(platform);

        return "redirect:/admin/platforms";
    }

    @PostMapping("/admin/platforms/delete/{id}")
    public String deletePlatform(@PathVariable Integer id) {
        streamingPlatformService.delete(id);
        return "redirect:/admin/platforms";
    }
}
