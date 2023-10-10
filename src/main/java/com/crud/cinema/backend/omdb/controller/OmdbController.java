package com.crud.cinema.backend.omdb.controller;

import com.crud.cinema.backend.omdb.domain.OmdbMovieDto;
import com.crud.cinema.backend.omdb.facade.OmdbFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/omdb")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OmdbController {

    private final OmdbFacade omdbFacade;

    @GetMapping("movies/{movieTitle}")
    public ResponseEntity<OmdbMovieDto> getOmdbMovie(@PathVariable String movieTitle) {
        return ResponseEntity.ok(omdbFacade.getOmdbMovieByTitle(movieTitle));
    }
}
