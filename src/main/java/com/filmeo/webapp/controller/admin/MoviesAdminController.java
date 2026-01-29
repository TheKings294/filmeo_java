package com.filmeo.webapp.controller.admin;

import com.filmeo.webapp.model.dto.genre.GenreDTO;
import com.filmeo.webapp.model.dto.human.HumanDTO;
import com.filmeo.webapp.model.dto.nationality.NationalityDTO;
import com.filmeo.webapp.model.dto.platform.StreamingPlatformDTO;
import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.model.formEntity.MovieForm;
import com.filmeo.webapp.model.formEntity.PlatformMovieForm;
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
public class MoviesAdminController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private HumanService humanService;

    @Autowired
    private NationalityService nationalityService;

    @Autowired
    private PlatformMovieService platformMovieService;

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @GetMapping("/admin/movies")
    public String showMovieList(Model model) {
        model.addAttribute("movies", movieService.selectAll());
        return "admin/movie/movies";
    }

    @GetMapping("/admin/movies/new")
    public String showMovieForm(
            Model model
    ) {

        model.addAttribute("movieForm", new MovieForm());
        model.addAttribute("genres", genreService.selectAll().stream().map(GenreDTO::new).toList());
        model.addAttribute("humans", humanService.selectAll().stream().map(HumanDTO::new).toList());
        model.addAttribute("castings", humanService.selectAll().stream().map(HumanDTO::new).toList());
        model.addAttribute("nationalities",
                nationalityService.selectAll().stream().map(NationalityDTO::new).toList());
        model.addAttribute("platforms",
                streamingPlatformService.selectAll().stream().map(StreamingPlatformDTO::new).toList());

        return "admin/movie/form";
    }

    @PostMapping("/admin/movies/new")
    public String newMovie(
            @Valid MovieForm movieForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.selectAll());
            model.addAttribute("humans", humanService.selectAll());
            model.addAttribute("castings", humanService.selectAll());
            model.addAttribute("nationalities", nationalityService.selectAll());
            model.addAttribute("platforms", streamingPlatformService.selectAll());
            return "admin/movie/form";
        }

        Movie movie = new Movie();
        movie.setTitle(movieForm.getTitle());
        movie.setResume(movieForm.getResume());
        movie.setPosterURL(movieForm.getPosterURL());
        movie.setRealisator(humanService.selectById(movieForm.getRealId()));

        movie.setGenres(genreService.selectByIds(movieForm.getGenresId()));
        movie.setCasting(humanService.selectByIds(movieForm.getCastingId()));
        movie.setNationalities(nationalityService.selectByIds(movieForm.getNationalitiesId()));

        movieService.insert(movie);

        for (PlatformMovieForm pmForm : movieForm.getPlatformMoviesId()) {
            PlatformMovie pm = new PlatformMovie();
            pm.setMovie(movie);
            pm.setPlatform(streamingPlatformService.selectById(pmForm.getPlatformId()));
            pm.setEndDate(pmForm.getEndDate());

            platformMovieService.insert(pm);
        }

        return "redirect:/admin/movies";
    }

    @GetMapping("/admin/movies/update/{id}")
    public String showUpdateForm(
            @PathVariable Integer id,
            Model model
    ) {
        Movie movie = movieService.selectById(id);

        MovieForm movieForm = new MovieForm();
        movieForm.setTitle(movie.getTitle());
        movieForm.setResume(movie.getResume());
        movieForm.setPosterURL(movie.getPosterURL());
        movieForm.setRealId(movie.getRealisator().getId());

        movieForm.setGenresId(
                movie.getGenres().stream().map(Genre::getId).toList()
        );
        movieForm.setCastingId(
                movie.getCasting().stream().map(Human::getId).toList()
        );
        movieForm.setNationalitiesId(
                movie.getNationalities().stream().map(Nationality::getId).toList()
        );

        model.addAttribute("movieForm", movieForm);
        model.addAttribute("movieId", id);

        model.addAttribute("genres", genreService.selectAll().stream().map(GenreDTO::new).toList());
        model.addAttribute("humans", humanService.selectAll().stream().map(HumanDTO::new).toList());
        model.addAttribute("castings", humanService.selectAll().stream().map(HumanDTO::new).toList());
        model.addAttribute("nationalities",
                nationalityService.selectAll().stream().map(NationalityDTO::new).toList());
        model.addAttribute("platforms",
                streamingPlatformService.selectAll().stream().map(StreamingPlatformDTO::new).toList());

        return "admin/movie/form";
    }

    @PostMapping("/admin/movies/update/{id}")
    public String updateMovie(
            @PathVariable Integer id,
            @Valid MovieForm movieForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/movie/form";
        }

        Movie movie = movieService.selectById(id);
        movie.setTitle(movieForm.getTitle());
        movie.setResume(movieForm.getResume());
        movie.setPosterURL(movieForm.getPosterURL());
        movie.setRealisator(humanService.selectById(movieForm.getRealId()));

        movie.setGenres(genreService.selectByIds(movieForm.getGenresId()));
        movie.setCasting(humanService.selectByIds(movieForm.getCastingId()));
        movie.setNationalities(nationalityService.selectByIds(movieForm.getNationalitiesId()));

        movieService.update(movie);

        for (PlatformMovieForm pmForm : movieForm.getPlatformMoviesId()) {
            PlatformMovie pm = new PlatformMovie();
            pm.setMovie(movie);
            pm.setPlatform(streamingPlatformService.selectById(pmForm.getPlatformId()));
            pm.setEndDate(pmForm.getEndDate());

            platformMovieService.update(pm);
        }

        return "redirect:/admin/movies";
    }

    @PostMapping("/admin/movies/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.delete(id);
        return "redirect:/admin/movies";
    }
}