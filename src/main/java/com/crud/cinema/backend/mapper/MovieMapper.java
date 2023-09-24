package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieMapper {

    public Movie mapToMovie(final MovieDto movieDto) {
        return new Movie(
                movieDto.getId(),
                movieDto.getTitle(),
                movieDto.getDescription(),
                movieDto.getYear()
        );
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getYear(),
                movie.getPerformances()
        );
    }

    public List<MovieDto> mapToMovieDtoList(final List<Movie> movies) {
        return movies.stream()
                .map(this::mapToMovieDto)
                .collect(Collectors.toList());
    }
}
