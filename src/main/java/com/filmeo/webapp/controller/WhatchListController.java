package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.dto.MediaDTO;
import com.filmeo.webapp.model.entity.ConnectedUser;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.service.MovieService;
import com.filmeo.webapp.model.service.SeriService;
import com.filmeo.webapp.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WhatchListController {
    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriService seriService;

    @GetMapping("/mywhatchlist")
    public String showWhatchList(
            Model model,
            @AuthenticationPrincipal ConnectedUser userDetail
    ) {
        User user = userService.selectById(userDetail.getUser().getId());
        List<MediaDTO> whatchList = new ArrayList<>();
        Map<Integer, Double> avgByMovieId = new HashMap<>();
        Map<Integer, Double> avgBySeriId = new HashMap<>();

        user.getWishListMovie().forEach(movie -> {
            whatchList.add(MediaDTO.fromMovie(movie));
            avgByMovieId.put(movie.getId(), movieService.avgRate(movie.getId()));
        });
        user.getWishListSeri().forEach(seri -> {
            whatchList.add(MediaDTO.fromSeries(seri));
            avgBySeriId.put(seri.getId(), seriService.avgRate(seri.getId()));
        });

        model.addAttribute("medias", whatchList);
        model.addAttribute("avgByMovieId", avgByMovieId);
        model.addAttribute("avgBySeriId", avgBySeriId);
        model.addAttribute("content", "public/whatchlist/whatchlist");

        return "base";
    }

    @PostMapping("/mywhatchlist/toggle/movie/{id}")
    public String toggleMovie(
            Model model,
            @PathVariable Integer id,
            @AuthenticationPrincipal ConnectedUser userDetail
    ) {
        Movie movie = movieService.selectById(id);
        User user = userService.selectById(userDetail.getUser().getId());
        List<Movie> whatchlist = user.getWishListMovie();
        whatchlist.add(movie);
        user.setWishListMovie(whatchlist);
        userService.update(user);

        return "redirect:/mywhatchlist";
    }

    @PostMapping("/mywhatchlist/toggle/seri/{id}")
    public String toggleSeri(
            Model model,
            @PathVariable Integer id,
            @AuthenticationPrincipal ConnectedUser userDetail
    ) {
        Seri seri = seriService.selectById(id);
        User user = userService.selectById(userDetail.getUser().getId());
        List<Seri> whatchlist = user.getWishListSeri();
        whatchlist.add(seri);
        user.setWishListSeri(whatchlist);
        userService.update(user);

        return "redirect:/mywhatchlist";
    }
}
