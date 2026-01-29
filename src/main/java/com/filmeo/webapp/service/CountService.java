package com.filmeo.webapp.service;

import com.filmeo.webapp.model.dto.CountDTO;
import com.filmeo.webapp.model.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountService {

    private final MovieService movieService;
    private final GenreService genreService;
    private final HumanService humanService;
    private final NationalityService nationalityService;
    private final StreamingPlatformService streamingPlatformService;
    private final SeriService seriService;
    private final UserService userService;

    public CountDTO getTotalCount() {
        CountDTO c = new CountDTO();

        c.setMovies(movieService.selectAll().size());
        c.setSeries(seriService.selectAll().size());
        c.setHumans(humanService.selectAll().size());
        c.setGenres(genreService.selectAll().size());
        c.setNationalities(nationalityService.selectAll().size());
        c.setPlatforms(streamingPlatformService.selectAll().size());
        c.setUsers(userService.selectAll().size());

        return c;
    }
}

