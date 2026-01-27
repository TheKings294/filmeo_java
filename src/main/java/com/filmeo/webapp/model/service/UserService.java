package com.filmeo.webapp.model.service;

import com.filmeo.webapp.error.BusinessException;
import com.filmeo.webapp.error.ErrorType;
import com.filmeo.webapp.model.entity.Movie;
import com.filmeo.webapp.model.entity.Seri;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> selectAll() {
        return this.userRepository.findAll();
    }

    public User selectById(Integer id) throws BusinessException {
        return this.userRepository.findById(id).orElseThrow(
                () -> new BusinessException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "User"
                )
        );
    }

    public User insert(User user) {
        return this.userRepository.save(user);
    }

    public User update(User user) {
        return this.userRepository.save(user);
    }

    public boolean delete(User user) {
        if (!this.userRepository.existsById(user.getId())) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("user %d", user.getId())
        );

        this.userRepository.delete(user);
        return true;
    }

    public boolean delete(Integer id) {
        if (!this.userRepository.existsById(id)) throw new BusinessException(
                ErrorType.ENTITY_NOT_FOUND,
                String.format("user %d", id)
        );

        this.userRepository.deleteById(id);
        return true;
    }

    public boolean mediaIsInWatchList(Integer id, Movie movie) {
        return this.selectById(id).getWishListMovie().contains(movie);
    }

    public boolean mediaIsInWatchList(Integer id, Seri seri) {
        return this.selectById(id).getWishListSeri().contains(seri);
    }
}
