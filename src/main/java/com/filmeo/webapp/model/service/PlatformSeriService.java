package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.PlatformSeri;
import com.filmeo.webapp.model.repository.PlatformSeriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformSeriService {
    @Autowired
    private PlatformSeriRepository platformSeriRepository;

    public List<PlatformSeri> selectAll() {
        return this.platformSeriRepository.findAll();
    }

    public PlatformSeri selectById(Integer id) throws BusinessException {
        return this.platformSeriRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Platform seri"
                )
        );
    }

    public PlatformSeri insert(PlatformSeri platformSeri) {
        return this.platformSeriRepository.save(platformSeri);
    }

    public PlatformSeri update(PlatformSeri platformSeri) {
        return this.platformSeriRepository.save(platformSeri);
    }

    public boolean delete(PlatformSeri platformSeri) {
        if (!this.platformSeriRepository.existsById(platformSeri.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform seri %d", platformSeri.getId())
        );

        this.platformSeriRepository.delete(platformSeri);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.platformSeriRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform seri %d", id)
        );

        this.platformSeriRepository.deleteById(id);
        return true;
    }
}
