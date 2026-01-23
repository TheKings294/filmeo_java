package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.PlatformMovie;
import com.filmeo.webapp.model.repository.PlatformMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformMovieService {
    @Autowired
    private PlatformMovieRepository platformMovieRepository;

    public List<PlatformMovie> selectAll() {
        return this.platformMovieRepository.findAll();
    }

    public PlatformMovie selectById(Integer id) throws BusinessException {
        return this.platformMovieRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Platform Movie"
                )
        );
    }

    public PlatformMovie insert(PlatformMovie platformMovie) {
        return this.platformMovieRepository.save(platformMovie);
    }

    public PlatformMovie update(PlatformMovie platformMovie) {
        return this.platformMovieRepository.save(platformMovie);
    }

    public boolean delete(PlatformMovie platformMovie) {
        if (!this.platformMovieRepository.existsById(platformMovie.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform movie %d", platformMovie.getId())
        );

        this.platformMovieRepository.delete(platformMovie);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.platformMovieRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform movie %d", id)
        );

        this.platformMovieRepository.deleteById(id);
        return true;
    }
}
