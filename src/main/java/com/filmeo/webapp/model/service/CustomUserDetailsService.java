package com.filmeo.webapp.model.service;

import com.filmeo.webapp.model.entity.ConnectedUser;
import com.filmeo.webapp.model.entity.User;
import com.filmeo.webapp.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );
        return new ConnectedUser(user);
    }

    /**
     * Optional: Additional method to get user by ID
     * Useful for getting current logged-in user details
     */
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );
    }

    /**
     * Optional: Method to check if username exists
     * Useful during registration
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByEmail(username);
    }
}
