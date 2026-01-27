package com.filmeo.webapp.model.dto.user;

import com.filmeo.webapp.model.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserLightDTO {
    private String pseudo;
    private String email;
    private List<String> roles;

    public UserLightDTO(User user) {
        this.pseudo = user.getPseudo();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
