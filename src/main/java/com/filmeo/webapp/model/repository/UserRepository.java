package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wishListMovie WHERE u.id = :userId")
    User findByIdWithWishListMovie(@Param("userId") Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wishListSeri WHERE u.id = :userId")
    User findByIdWithWishListSeri(@Param("userId") Long userId);
}
