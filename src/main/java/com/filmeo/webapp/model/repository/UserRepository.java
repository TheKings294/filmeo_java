package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
