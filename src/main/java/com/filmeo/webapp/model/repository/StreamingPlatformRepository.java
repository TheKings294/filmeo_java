package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.StreamingPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingPlatformRepository extends JpaRepository<StreamingPlatform, Integer> {
}
