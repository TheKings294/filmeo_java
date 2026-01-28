package com.filmeo.webapp.model.repository;

import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Seri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeriRepository extends JpaRepository<Seri, Integer> {

    @Query("SELECT DISTINCT s FROM Seri s JOIN s.casting c WHERE c.id = :actorId")
    List<Seri> findSeriesByActorId(@Param("actorId") Integer actorId);
}
