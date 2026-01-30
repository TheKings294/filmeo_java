package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.entity.Nationality;
import com.filmeo.webapp.model.repository.NationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NationalityService {
    @Autowired
    private NationalityRepository nationalityRepository;

    public List<Nationality> selectAll() {
        return this.nationalityRepository.findAll();
    }

    public Page<Nationality> selectAll(Pageable pageable) {
        return this.nationalityRepository.findAll(pageable);
    }

    public Nationality selectById(Integer id) throws BusinessException {
        return this.nationalityRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Nationality"
                )
        );
    }

    public List<Nationality> selectByIds(List<Integer> integers) {
        List<Nationality> nationalities = new ArrayList<>();
        integers.forEach(number -> {
            nationalities.add(selectById(number));
        });

        return nationalities;
    }

    public Nationality insert(Nationality nationality) {
        return this.nationalityRepository.save(nationality);
    }

    public Nationality update(Nationality nationality) {
        return this.nationalityRepository.save(nationality);
    }

    public boolean delete(Nationality nationality) {
        if (!this.nationalityRepository.existsById(nationality.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("nationality %d", nationality.getId())
        );

        this.nationalityRepository.delete(nationality);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.nationalityRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("nationality %d", id)
        );

        this.nationalityRepository.deleteById(id);
        return true;
    }
}
