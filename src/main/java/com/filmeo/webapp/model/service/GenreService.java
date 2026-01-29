package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Genre;
import com.filmeo.webapp.model.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> selectAll() {
        return this.genreRepository.findAll();
    }

    public Genre selectById(Integer id) throws BusinessException {
        return this.genreRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Genre"
                )
        );
    }

    public List<Genre> selectByIds(List<Integer> integers) {
        List<Genre> genres = new ArrayList<>();
        integers.forEach(number -> {
            genres.add(selectById(number));
        });

        return genres;
    }

    public Genre insert(Genre genre) {
        return this.genreRepository.save(genre);
    }

    public Genre update(Genre genre) {
        return this.genreRepository.save(genre);
    }

    public boolean delete(Genre genre) {
        if (!this.genreRepository.existsById(genre.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("genre %d", genre.getId())
        );

        this.genreRepository.delete(genre);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.genreRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("genre %d", id)
        );

        this.genreRepository.deleteById(id);
        return true;
    }
}
