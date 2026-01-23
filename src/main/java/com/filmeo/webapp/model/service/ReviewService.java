package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Review;
import com.filmeo.webapp.model.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> selectAll() {
        return this.reviewRepository.findAll();
    }

    public Review selectById(Integer id) throws BusinessException {
        return this.reviewRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Review"
                )
        );
    }

    public Review insert(Review review) {
        return this.reviewRepository.save(review);
    }

    public Review update(Review review) {
        return this.reviewRepository.save(review);
    }

    public boolean delete(Review review) {
        if (!this.reviewRepository.existsById(review.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("review %d", review.getId())
        );

        this.reviewRepository.delete(review);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.reviewRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("review %d", id)
        );

        this.reviewRepository.deleteById(id);
        return true;
    }
}
