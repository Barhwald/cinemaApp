package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieMapperTest {

    @Autowired
    private MovieMapper movieMapper;

    @Test
    void shouldMapToMovie() {
        //Given
        MovieDto movieDto = new MovieDto(1L, "Title", "Desc", "2002");

        //When
        Movie movie = movieMapper.mapToMovie(movieDto);

        //Then
        assertEquals(1, movie.getId());
        assertEquals("Title", movie.getTitle());
        assertEquals("Desc", movie.getDescription());
        assertEquals("2002", movie.getYear());
    }

    @Test
    void shouldMapToMovieDto() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");

        //When
        MovieDto movieDto = movieMapper.mapToMovieDto(movie);

        //Then
        assertEquals(1, movieDto.getId());
        assertEquals("Title", movieDto.getTitle());
        assertEquals("Desc", movieDto.getDescription());
        assertEquals("2002", movieDto.getYear());
    }

    @Test
    void shouldMapToMovieDtoList() {
        //Given
        Movie movie1 = new Movie(1L, "Title1", "Desc", "2002");
        Movie movie2 = new Movie(1L, "Title2", "Desc", "2002");
        Movie movie3 = new Movie(1L, "Title3", "Desc", "2002");
        Movie movie4 = new Movie(1L, "Title4", "Desc", "2002");
        List<Movie> movieList = List.of(movie1, movie2, movie3, movie4);

        //When
        List<MovieDto> movieDtoList = movieMapper.mapToMovieDtoList(movieList);

        //Then
        assertEquals(4, movieDtoList.size());
        assertEquals("Title1", movieDtoList.get(0).getTitle());
        assertEquals("Title2", movieDtoList.get(1).getTitle());
        assertEquals("Title3", movieDtoList.get(2).getTitle());
        assertEquals("Title4", movieDtoList.get(3).getTitle());

    }
}