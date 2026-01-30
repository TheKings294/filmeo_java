package com.filmeo.webapp.model.dto.user;

import com.filmeo.webapp.model.dto.movie.MovieDTO;
import com.filmeo.webapp.model.dto.movie.MovieLightDTO;
import com.filmeo.webapp.model.dto.seri.SeriDTO;
import com.filmeo.webapp.model.dto.seri.SeriLightDTO;
import com.filmeo.webapp.model.entity.Review;
import com.filmeo.webapp.model.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String pseudo;
    private String email;
    private List<String> roles;
    private List<Review> reviews;
    private List<MovieLightDTO> wishListMovie;
    private List<SeriLightDTO> wishListSeri;

    public UserDTO(User user) {
        this.id = user.getId();
        this.pseudo = user.getPseudo();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.reviews = user.getReviews();
        this.wishListMovie = user.getWishListMovie().stream().map(MovieLightDTO::new).toList();
        this.wishListSeri = user.getWishListSeri().stream().map(SeriLightDTO::new).toList();
    }
}
