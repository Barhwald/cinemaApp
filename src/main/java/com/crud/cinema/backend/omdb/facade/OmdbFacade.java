package com.crud.cinema.backend.omdb.facade;

import com.crud.cinema.backend.omdb.domain.OmdbMovieDto;
import com.crud.cinema.backend.omdb.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OmdbFacade {

    private final OmdbService omdbService;

    public OmdbMovieDto getOmdbMovieByTitle(String title) {
        return omdbService.getOmdbMovieByTitle(title);
    }
}
