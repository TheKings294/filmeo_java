package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Integer> {
}
