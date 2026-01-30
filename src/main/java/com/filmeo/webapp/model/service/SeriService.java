package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.PlatformMovie;
import com.filmeo.webapp.model.entity.PlatformSeri;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.formEntity.PlatformMovieForm;
import com.filmeo.webapp.model.formEntity.PlatformSeriForm;
import com.filmeo.webapp.model.repository.PlatformSeriRepository;
import com.filmeo.webapp.model.repository.SeriRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeriService {
    @Autowired
    private SeriRepository seriRepository;

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @Autowired
    private EntityManager entityManager;

    public List<Seri> selectAll() {
        return this.seriRepository.findAll();
    }

    public Page<Seri> selectAll(Pageable pageable) {
        return this.seriRepository.findAll(pageable);
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

    public List<Seri> getMoviesByActor(Integer actorId) {
        return seriRepository.findSeriesByActorId(actorId);
    }

    public Double avgRate(Integer id) {
        return entityManager.createQuery(
                        """
                        SELECT AVG(r.rate)
                        FROM Review r
                        WHERE r.seri.id = :seriId
                        """,
                        Double.class
                )
                .setParameter("seriId", id)
                .getSingleResult();
    }

    public void deletePlatformSeri(Seri seri) {
        seri.getPlatformSeris().clear();
    }

    public void addPlatformSeri(Seri seri, PlatformSeri pm) {
        seri.getPlatformSeris().add(pm);
        pm.setSeri(seri);
    }

    @Transactional
    public void updatePlatformSeries(Seri seri, List<PlatformSeriForm> platformForms) {
        // Ensure managed entity
        Seri managedSeri = seriRepository.findById(seri.getId())
                .orElseThrow(() -> new RuntimeException("Seri not found"));

        // Delete all old platforms
        managedSeri.getPlatformSeris().clear();

        // Add new platforms
        for (PlatformSeriForm pmForm : platformForms) {
            PlatformSeri pm = new PlatformSeri();
            pm.setSeri(managedSeri);
            pm.setPlatform(streamingPlatformService.selectById(pmForm.getPlatformId()));
            pm.setEndDate(pmForm.getEndDate());

            managedSeri.getPlatformSeris().add(pm); // Hibernate will persist automatically
        }

        // Optional: save parent, Hibernate flushes automatically due to @Transactional
        seriRepository.save(managedSeri);
    }

    public List<Seri> searchSeries(String keyWord) {
        return seriRepository.searchByKeyword(keyWord);
    }
}
