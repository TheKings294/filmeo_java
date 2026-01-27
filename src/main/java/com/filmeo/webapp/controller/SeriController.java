package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.seri.SeriDTO;
import com.filmeo.webapp.model.service.SeriService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SeriController {
    @Autowired
    private SeriService seriService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/series")
    public String showSeries(
            Model model
    ) {
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

        model.addAttribute("series", seriService.selectAll().stream().map(SeriDTO::new).toList());
        model.addAttribute("avgBySeriId", avgBySeriId);
        model.addAttribute("content", "public/series");

        return "base";
    }
}
