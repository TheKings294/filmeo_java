package com.filmeo.webapp.model.dto.review;

import com.filmeo.webapp.model.entity.Review;
import lombok.Data;

@Data
public class ReviewLightDTO {
    private String comment;
    private Integer rate;

    public ReviewLightDTO(Review review) {
        this.comment = review.getComment();
        this.rate = review.getRate();
    }
}
