package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.genre.GenreDTO;
import com.filmeo.webapp.model.dto.human.HumanDTO;
import com.filmeo.webapp.model.dto.platformseries.PlatformSeriesDTO;
import com.filmeo.webapp.model.dto.review.ReviewDTO;
import com.filmeo.webapp.model.dto.seri.SeriDTO;
import com.filmeo.webapp.model.entity.ConnectedUser;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.service.ReviewService;
import com.filmeo.webapp.model.service.SeriService;
import com.filmeo.webapp.model.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SeriController {
    @Autowired
    private SeriService seriService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/series")
    public String showSeries(
            Model model,
            @RequestParam(required = false) Integer pageNumber
    ) {
        if (pageNumber == null) pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 20);
        List<Object[]> results = entityManager.createQuery(
                """
                SELECT r.seri.id, avg(r.rate)
                from Review  r
                group by r.seri.id
                """,
                Object[].class
        ).getResultList();
        Map<Integer, Double> avgBySeriId = results.stream()
                .collect(
                        Collectors.toMap(
                                r -> (Integer) r[0],
                                r -> (Double) r[1]
                        )
                );
        Page<SeriDTO> page = seriService.selectAll(pageable).map(SeriDTO::new);

        model.addAttribute("series", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("avgBySeriId", avgBySeriId);
        model.addAttribute("content", "public/seri/series");

        return "base";
    }

    @GetMapping("/series/{id}")
    public String showOneSeri(
            Model model,
            @PathVariable Integer id,
            @AuthenticationPrincipal ConnectedUser connectedUser
    ) {
        Double avgRate = entityManager.createQuery(
                        """
                        SELECT AVG(r.rate)
                        FROM Review r
                        WHERE r.seri.id = :seriId
                        """,
                        Double.class
                )
                .setParameter("seriId", id)
                .getSingleResult();

        Seri seri = seriService.selectById(id);

        boolean isInWatchList = false;
        if (connectedUser != null) {
            isInWatchList = userService.mediaIsInWatchList(connectedUser.getUser().getId(), seri);
        }

        model.addAttribute("averageRating", avgRate != null ? avgRate : 0.0);
        model.addAttribute("media", new SeriDTO(seri));
        model.addAttribute("genres", seri.getGenres().stream().map(GenreDTO::new).toList());
        model.addAttribute("actors", seri.getCasting().stream().map(HumanDTO::new).toList());
        model.addAttribute("comments", reviewService.getSeriReviews(seri.getId()).stream().map(ReviewDTO::new).toList());
        model.addAttribute("platforms", seri.getPlatformSeris().stream().map(PlatformSeriesDTO::new).toList());
        model.addAttribute("directors", new ArrayList<>());
        model.addAttribute("inWatchlist", isInWatchList);
        model.addAttribute("content", "public/seri/serie-description");

        return "base";
    }
}
