package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.genre.GenreDTO;
import com.filmeo.webapp.model.entity.Genre;
import com.filmeo.webapp.model.formEntity.GenreForm;
import com.filmeo.webapp.model.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenresAdminController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/admin/genres")
    public String showGenreList(
            Model model,
            @RequestParam(required = false) Integer pageNumber
    ) {
        if (pageNumber == null) pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<GenreDTO> page = genreService.selectAll(pageable)
                .map(GenreDTO::new);
        model.addAttribute("genres", page);

        return "admin/genre/genres";
    }

    @GetMapping("/admin/genres/new")
    public String showForm(
            Model model
    ) {
        model.addAttribute("genreForm", new GenreForm());
        model.addAttribute("editMode", false);

        return "admin/genre/form";
    }

    @PostMapping("/admin/genres/new")
    public String newGenre(
            Model model,
            @Valid GenreForm genreForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genreForm", genreForm);
            model.addAttribute("editMode", false);
            return "admin/genre/form";
        }

        Genre genre = new Genre();
        genre.setName(genreForm.getName());
        genre.setDescription(genreForm.getDescription());

        genreService.insert(genre);

        return "redirect:/admin/genres";
    }

    @GetMapping("/admin/genres/update/{id}")
    public String showForm(
            Model model,
            @PathVariable Integer id
    ) {
        Genre genre = genreService.selectById(id);
        GenreForm genreForm = new GenreForm();
        genreForm.setName(genre.getName());
        genreForm.setDescription(genre.getDescription());

        model.addAttribute("genreForm", genreForm);
        model.addAttribute("genreId", id);
        model.addAttribute("editMode", true);

        return "admin/genre/form";
    }

    @PostMapping("/admin/genres/update/{id}")
    public String updateGenre(
            Model model,
            @Valid GenreForm genreForm,
            @PathVariable Integer id,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genreForm", genreForm);
            model.addAttribute("genreId", id);
            model.addAttribute("editMode", true);
            return "admin/genre/form";
        }

        Genre updateGenre = genreService.selectById(id);
        updateGenre.setDescription(genreForm.getDescription());
        updateGenre.setName(genreForm.getName());

        genreService.update(updateGenre);

        return "redirect:/admin/genres";
    }

    @PostMapping("/admin/genres/delete/{id}")
    public String deleteGenre(
            Model model,
            @PathVariable Integer id
    ) {
        genreService.delete(id);

        return "redirect:/admin/genres";
    }
}
