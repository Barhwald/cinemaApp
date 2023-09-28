package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.mapper.MovieMapper;
import com.crud.cinema.backend.service.MovieDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieFacade {

    private final MovieDbService movieDbService;
    private final MovieMapper movieMapper;

    public List<MovieDto> getMoviesList() {
        List<Movie> movies = movieDbService.getAllMovies();
        return movies.stream()
                .map(movieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieWithId(Long movieId) {
        Movie movie = movieDbService.getMovieWithId(movieId);
        return movieMapper.mapToMovieDto(movie);
    }

    public void createMovie(final MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        movieDbService.saveMovie(movie);
    }

    public MovieDto updateMovie(final MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        Movie savedMovie = movieDbService.saveMovie(movie);
        return movieMapper.mapToMovieDto(savedMovie);
    }

    public void deleteMovie(Long movieId) {
        movieDbService.deleteMovieById(movieId);
    }
}
