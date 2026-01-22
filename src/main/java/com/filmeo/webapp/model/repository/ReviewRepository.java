package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
