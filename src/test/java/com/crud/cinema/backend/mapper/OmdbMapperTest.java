package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.domain.OmdbMovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OmdbMapperTest {

    @Autowired
    private OmdbMapper omdbMapper;

    @Test
    void shouldMapToMovieDto() {
        //Given
        OmdbMovieDto omdbMovieDto = new OmdbMovieDto("Title", "Desc", "2010", "2.8");

        //When
        MovieDto movieDto = omdbMapper.maptoMovieDto(omdbMovieDto);

        //Then
        assertEquals("Title", movieDto.getTitle());
        assertEquals("Desc", movieDto.getDescription());
        assertEquals("2010", movieDto.getYear());
    }
}