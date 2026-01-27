package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.MediaDTO;
import com.filmeo.webapp.model.dto.human.HumanDTO;
import com.filmeo.webapp.model.dto.movie.MovieDTO;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.service.HumanService;
import com.filmeo.webapp.model.service.MovieService;
import com.filmeo.webapp.model.service.SeriService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActorController {
    @Autowired
    private HumanService humanService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriService seriService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/actors/{id}")
    public String showOneHuman(
            Model model,
            @PathVariable Integer id
    ) {
        Human human = humanService.selectById(id);

        List<Movie> directedContent = human.getRealisation();
        List<MediaDTO> actedContent = new ArrayList<>();
        Map<Integer, Double> avgByMovieId = new HashMap<>();
        Map<Integer, Double> avgBySeriId = new HashMap<>();

        movieService.getMoviesByActor(human.getId()).forEach(movie -> {
                    actedContent.add(MediaDTO.fromMovie(movie));
                    avgByMovieId.put(movie.getId(), movieService.avgRate(movie.getId()));
                });
        seriService.getMoviesByActor(human.getId()).forEach(seri -> {
            actedContent.add(MediaDTO.fromSeries(seri));
            avgBySeriId.put(seri.getId(), seriService.avgRate(seri.getId()));
        });

        model.addAttribute("human", new HumanDTO(human));
        model.addAttribute("filmography", actedContent.size());
        model.addAttribute("directedContent", directedContent.stream().map(MovieDTO::new).toList());
        model.addAttribute("actedContent", actedContent);
        model.addAttribute("avgByMovieId", avgByMovieId);
        model.addAttribute("avgBySeriId", avgBySeriId);
        model.addAttribute("age", Period.between(human.getBirthDate(), LocalDate.now()).getYears());
        model.addAttribute("content", "public/actor/actor-description");

        return "base";
    }
}
