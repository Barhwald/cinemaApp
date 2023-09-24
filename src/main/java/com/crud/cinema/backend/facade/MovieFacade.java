package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.mapper.MovieMapper;
import com.crud.cinema.backend.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieFacade {

    private final DbService dbService;
    private final MovieMapper movieMapper;

    public List<MovieDto> getMoviesList() {
        List<Movie> movies = dbService.getAllMovies();
        return movies.stream()
                .map(movieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieWithId(Long movieId) {
        Movie movie = dbService.getMovieWithId(movieId);
        return movieMapper.mapToMovieDto(movie);
    }

    public void createMovie(final MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        dbService.saveMovie(movie);
    }

    public MovieDto updateMovie(final MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        Movie savedMovie = dbService.saveMovie(movie);
        return movieMapper.mapToMovieDto(savedMovie);
    }

    public void deleteMovie(Long movieId) {
        dbService.deleteMovieById(movieId);
    }
}
