package com.crud.cinema.backend.omdb.mapper;

import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.omdb.domain.OmdbMovieDto;
import org.springframework.stereotype.Service;

@Service
public class OmdbMapper {

    public MovieDto maptoMovieDto(final OmdbMovieDto omdbMovieDto) {
        return new MovieDto(
                omdbMovieDto.getTitle(),
                omdbMovieDto.getPlot(),
                omdbMovieDto.getYear()
        );
    }

}
