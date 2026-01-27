package com.filmeo.webapp.model.dto.review;

import com.filmeo.webapp.model.dto.human.HumanLightDTO;
import com.filmeo.webapp.model.dto.movie.MovieLightDTO;
import com.filmeo.webapp.model.dto.seri.SeriLightDTO;
import com.filmeo.webapp.model.dto.user.UserLightDTO;
import com.filmeo.webapp.model.entity.*;
import lombok.Data;

@Data
public class ReviewDTO {
    private String comment;
    private Integer rate;
    private UserLightDTO user;
    private SeriLightDTO seri;
    private MovieLightDTO movie;
    private HumanLightDTO human;

    public ReviewDTO(Review review) {
        this.comment = review.getComment();
        this.rate = review.getRate();
        this.user = new UserLightDTO(review.getUser());
        this.seri = new SeriLightDTO(review.getSeri());
        this.movie = new MovieLightDTO(review.getMovie());
        this.human = new HumanLightDTO(review.getActor());
    }
}
