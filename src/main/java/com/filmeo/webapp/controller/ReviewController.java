package com.filmeo.webapp.controller;

import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.model.formEntity.ReviewForm;
import com.filmeo.webapp.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriService seriService;

    @Autowired
    private HumanService humanService;

    @PostMapping("/reviews/add/{id}")
    public String addAnReview(
            @PathVariable Integer id,
            @ModelAttribute ReviewForm reviewForm,
            @RequestParam(required = false) String redirect,
            @AuthenticationPrincipal ConnectedUser connectedUser
    ) {
        User user = userService.selectById(connectedUser.getUser().getId());
        Review review = new Review();
        review.setRate(reviewForm.getRate());
        review.setComment(reviewForm.getComment());
        review.setUser(user);

        switch (reviewForm.getType()) {
            case "movie":
                Movie movie = movieService.selectById(id);
                review.setMovie(movie);
                user.getWishListMovie().remove(movie);
                break;
            case "serie":
                Seri seri = seriService.selectById(id);
                review.setSeri(seri);
                user.getWishListSeri().remove(seri);
                break;
            case "actor":
                review.setActor(humanService.selectById(id));
                break;
        }

        reviewService.insert(review);

        if (redirect != null) {
            return "redirect:" + redirect;
        }

        return "redirect:/";
    }
}
