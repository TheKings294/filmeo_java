package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.genre.GenreDTO;
import com.filmeo.webapp.model.dto.human.HumanDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityDTO;
import com.filmeo.webapp.model.dto.platform.StreamingPlatformDTO;
import com.filmeo.webapp.model.dto.seri.SeriDTO;
import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.model.formEntity.PlatformSeriForm;
import com.filmeo.webapp.model.formEntity.SeriForm;
import com.filmeo.webapp.model.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeriesAdminController {
    @Autowired
    private SeriService seriService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private HumanService humanService;

    @Autowired
    private NationalityService nationalityService;

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @Autowired
    private PlatformSeriService platformSeriService;

    @GetMapping("/admin/series")
    public String showSeriList(Model model) {
        model.addAttribute("series", seriService.selectAll().stream().map(SeriDTO::new).toList());
        return "admin/seri/series";
    }

    @GetMapping("/admin/series/new")
    public String showSeriForm(Model model) {

        model.addAttribute("seriForm", new SeriForm());

        model.addAttribute("genres", genreService.selectAll().stream().map(GenreDTO::new).toList());
        model.addAttribute("castings", humanService.selectAll().stream().map(HumanDTO::new).toList());
        model.addAttribute("nationalities",
                nationalityService.selectAll().stream().map(NationalityDTO::new).toList());
        model.addAttribute("platforms",
                streamingPlatformService.selectAll().stream().map(StreamingPlatformDTO::new).toList());

        return "admin/seri/form";
    }

    @PostMapping("/admin/series/new")
    public String newSeri(
            @Valid SeriForm seriForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.selectAll().stream().map(GenreDTO::new).toList());
            model.addAttribute("castings", humanService.selectAll().stream().map(HumanDTO::new).toList());
            model.addAttribute("nationalities",
                    nationalityService.selectAll().stream().map(NationalityDTO::new).toList());
            model.addAttribute("platforms",
                    streamingPlatformService.selectAll().stream().map(StreamingPlatformDTO::new).toList());
            return "admin/seri/form";
        }

        Seri seri = new Seri();
        seri.setTitle(seriForm.getTitle());
        seri.setResume(seriForm.getResume());
        seri.setPosterURL(seriForm.getPosterURL());
        seri.setSeasons(seriForm.getSeasons());
        seri.setEpisode(seriForm.getEpisode());
        seri.setStaus(seriForm.getStatus());

        seri.setGenres(genreService.selectByIds(seriForm.getGenresId()));
        seri.setCasting(humanService.selectByIds(seriForm.getCastingId()));
        seri.setNationalities(nationalityService.selectByIds(seriForm.getNationalitiesId()));

        seriService.insert(seri);

        for (PlatformSeriForm psForm : seriForm.getPlatformSerisId()) {
            PlatformSeri ps = new PlatformSeri();
            ps.setSeri(seri);
            ps.setPlatform(streamingPlatformService.selectById(psForm.getPlatformId()));
            ps.setEndDate(psForm.getEndDate());

            platformSeriService.insert(ps);
        }

        return "redirect:/admin/series";
    }

    @GetMapping("/admin/series/update/{id}")
    public String showUpdateForm(
            @PathVariable Integer id,
            Model model
    ) {
        Seri seri = seriService.selectById(id);

        SeriForm seriForm = new SeriForm();
        seriForm.setTitle(seri.getTitle());
        seriForm.setResume(seri.getResume());
        seriForm.setPosterURL(seri.getPosterURL());
        seriForm.setSeasons(seri.getSeasons());
        seriForm.setEpisode(seri.getEpisode());
        seriForm.setStatus(seri.getStaus());

        seriForm.setGenresId(
                seri.getGenres().stream().map(Genre::getId).toList()
        );
        seriForm.setCastingId(
                seri.getCasting().stream().map(Human::getId).toList()
        );
        seriForm.setNationalitiesId(
                seri.getNationalities().stream().map(Nationality::getId).toList()
        );

        model.addAttribute("seriForm", seriForm);
        model.addAttribute("seriId", id);

        model.addAttribute("genres", genreService.selectAll());
        model.addAttribute("castings", humanService.selectAll());
        model.addAttribute("nationalities", nationalityService.selectAll());
        model.addAttribute("platforms", streamingPlatformService.selectAll());

        return "admin/seri/form";
    }

    @PostMapping("/admin/series/update/{id}")
    public String updateSeri(
            @PathVariable Integer id,
            @Valid SeriForm seriForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/seri/form";
        }

        Seri seri = seriService.selectById(id);
        seri.setTitle(seriForm.getTitle());
        seri.setResume(seriForm.getResume());
        seri.setPosterURL(seriForm.getPosterURL());
        seri.setSeasons(seriForm.getSeasons());
        seri.setEpisode(seriForm.getEpisode());
        seri.setStaus(seriForm.getStatus());

        seri.setGenres(genreService.selectByIds(seriForm.getGenresId()));
        seri.setCasting(humanService.selectByIds(seriForm.getCastingId()));
        seri.setNationalities(nationalityService.selectByIds(seriForm.getNationalitiesId()));

        seriService.update(seri);

        for (PlatformSeriForm psForm : seriForm.getPlatformSerisId()) {
            PlatformSeri ps = new PlatformSeri();
            ps.setSeri(seri);
            ps.setPlatform(streamingPlatformService.selectById(psForm.getPlatformId()));
            ps.setEndDate(psForm.getEndDate());

            platformSeriService.insert(ps);
        }

        return "redirect:/admin/series";
    }

    @PostMapping("/admin/series/delete/{id}")
    public String deleteSeri(@PathVariable Integer id) {
        seriService.delete(id);
        return "redirect:/admin/series";
    }
}
