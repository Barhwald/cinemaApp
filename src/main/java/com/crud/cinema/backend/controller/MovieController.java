package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.facade.MovieFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieFacade movieFacade;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(movieFacade.getMoviesList());
    }

    @GetMapping(value = "{movieId}")
    public ResponseEntity<MovieDto> getMovieWithId(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieFacade.getMovieWithId(movieId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMovie(@RequestBody MovieDto movieDto) {
        movieFacade.createMovie(movieDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieFacade.updateMovie(movieDto));
    }

    @DeleteMapping(value = "{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieFacade.deleteMovie(movieId);
        return ResponseEntity.ok().build();
    }
}
