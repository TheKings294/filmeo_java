package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.repository.SeriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriService {
    @Autowired
    private SeriRepository seriRepository;

    public List<Seri> selectAll() {
        return this.seriRepository.findAll();
    }

    public Seri selectById(Integer id) throws BusinessException {
        return this.seriRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Seri"
                )
        );
    }

    public Seri insert(Seri seri) {
        return this.seriRepository.save(seri);
    }

    public Seri update(Seri seri) {
        return this.seriRepository.save(seri);
    }

    public boolean delete(Seri seri) {
        if (!this.seriRepository.existsById(seri.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("seri %d", seri.getId())
        );

        this.seriRepository.delete(seri);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.seriRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("seri %d", id)
        );

        this.seriRepository.deleteById(id);
        return true;
    }
}
