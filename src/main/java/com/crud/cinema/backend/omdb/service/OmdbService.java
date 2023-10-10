package com.crud.cinema.backend.omdb.service;

import com.crud.cinema.backend.omdb.domain.OmdbMovieDto;
import com.crud.cinema.backend.omdb.client.OmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OmdbService {

    private final OmdbClient omdbClient;

    public OmdbMovieDto getOmdbMovieByTitle(String title) {
        return omdbClient.getOmdbMovieByTitle(title);
    }

}
