package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality, Integer> {
}
