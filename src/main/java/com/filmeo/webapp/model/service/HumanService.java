package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {
    @Autowired
    private HumanRepository humanRepository;

    public List<Human> selectAll() {
        return this.humanRepository.findAll();
    }

    public Human selectById(Integer id) throws BusinessException {
        return this.humanRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Human"
                )
        );
    }

    public Human insert(Human human) {
        return this.humanRepository.save(human);
    }

    public Human update(Human human) {
        return this.humanRepository.save(human);
    }

    public boolean delete(Human human) {
        if (!this.humanRepository.existsById(human.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("human %d", human.getId())
        );

        this.humanRepository.delete(human);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.humanRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("human %d", id)
        );

        this.humanRepository.deleteById(id);
        return true;
    }
}
