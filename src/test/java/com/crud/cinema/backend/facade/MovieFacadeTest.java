package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.mapper.MovieMapper;
import com.crud.cinema.backend.service.MovieDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
class MovieFacadeTest {

    @InjectMocks
    private MovieFacade movieFacade;
    @Mock
    private MovieDbService movieDbService;
    @Mock
    private MovieMapper movieMapper;

    @Test
    void shouldGetMoviesList() {
        //Given
        Movie movie1 = new Movie("Title", "Desc", "2000");
        Movie movie2 = new Movie("Title2", "Desc2", "2020");
        List<Movie> movieList = List.of(movie1, movie2);

        when(movieDbService.getAllMovies()).thenReturn(movieList);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesList();

        //Then
        assertEquals(2, movieDtoList.size());
    }

    @Test
    void shouldGetMovieWithId() {
        Movie movie1 = new Movie(1L, "Title", "Desc", "2000");
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Desc", "2000");

        when(movieDbService.getMovieWithId(1L)).thenReturn(movie1);
        when(movieMapper.mapToMovieDto(movie1)).thenReturn(movieDto1);

        //When
        MovieDto result = movieFacade.getMovieWithId(1L);

        //Then
        assertEquals("Title", result.getTitle());
        assertEquals("Desc", result.getDescription());
        assertEquals("2000", result.getYear());
    }

    @Test
    void shouldCreateMovie() {
        //Given
        Movie movie1 = new Movie(1L, "Title", "Desc", "2000");
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Desc", "2000");

        when(movieMapper.mapToMovie(movieDto1)).thenReturn(movie1);

        //When
        movieFacade.createMovie(movieDto1);

        //Then
        verify(movieMapper).mapToMovie(movieDto1);
        verify(movieDbService).saveMovie(movie1);
    }

    @Test
    void shouldUpdateMovie() {
        // Given
        MovieDto movieDto = new MovieDto("Titanic", "A ship", "1997");
        Movie movie = new Movie("Titanic", "A ship", "1997");
        Movie savedMovie = new Movie("Titanic", "A ship", "1997");

        when(movieMapper.mapToMovie(movieDto)).thenReturn(movie);
        when(movieDbService.saveMovie(movie)).thenReturn(savedMovie);
        when(movieMapper.mapToMovieDto(savedMovie)).thenReturn(movieDto);

        // When
        MovieDto updatedMovieDto = movieFacade.updateMovie(movieDto);

        // Then
        verify(movieMapper, times(1)).mapToMovie(movieDto);
        verify(movieDbService, times(1)).saveMovie(movie);
        verify(movieMapper, times(1)).mapToMovieDto(savedMovie);
        assertEquals(movieDto, updatedMovieDto);
    }

    @Test
    void shouldDeleteMovie() {
        //Given
        Long movieId = 1L;

        //When
        movieFacade.deleteMovie(movieId);

        //Then
        verify(movieDbService).deleteMovieById(movieId);
    }
}