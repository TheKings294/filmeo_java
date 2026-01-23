package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.StreamingPlatform;
import com.filmeo.webapp.model.repository.StreamingPlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamingPlatformService {
    @Autowired
    private StreamingPlatformRepository streamingPlatformRepository;

    public List<StreamingPlatform> selectAll() {
        return this.streamingPlatformRepository.findAll();
    }

    public StreamingPlatform selectById(Integer id) throws BusinessException {
        return this.streamingPlatformRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "StreamingPlatform"
                )
        );
    }

    public StreamingPlatform insert(StreamingPlatform streamingPlatform) {
        return this.streamingPlatformRepository.save(streamingPlatform);
    }

    public StreamingPlatform update(StreamingPlatform streamingPlatform) {
        return this.streamingPlatformRepository.save(streamingPlatform);
    }

    public boolean delete(StreamingPlatform streamingPlatform) {
        if (!this.streamingPlatformRepository.existsById(streamingPlatform.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform %d", streamingPlatform.getId())
        );

        this.streamingPlatformRepository.delete(streamingPlatform);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.streamingPlatformRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("platform %d", id)
        );

        this.streamingPlatformRepository.deleteById(id);
        return true;
    }
}
