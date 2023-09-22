package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.repository.MovieRepository;
import com.crud.cinema.backend.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MovieTestSuite {

    @Autowired
    private DbService dbService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void shouldSaveMovie() {
        //Given
        Movie movie = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", 2016);

        //When
        dbService.saveMovie(movie);
        Long movieId = movie.getId();

        //Then
        assertNotEquals(0, movieId);
    }

    @Test
    void shouldGetMovie() {
        //Given
        Movie movie = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", 2016);

        //When
        dbService.saveMovie(movie);
        Movie savedMovie = dbService.getMovieWithId(movie.getId());

        //Then
        assertEquals(savedMovie.getId(), movie.getId());
        assertEquals(savedMovie.getDescription(), movie.getDescription());
        assertEquals(savedMovie.getYear(), movie.getYear());
    }

    @Test
    void shouldGetAllMovies() {
        //Given
        Movie movie1 = new Movie("The Cause", "s get their home back", 2012);
        Movie movie2 = new Movie("The Bread", "A small crearves get their home back", 2011);
        Movie movie3 = new Movie("The Duck", "A small creature helps sir home back", 2013);
        Movie movie4 = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", 2018);

        //When
        dbService.saveMovie(movie1);
        dbService.saveMovie(movie2);
        dbService.saveMovie(movie3);
        dbService.saveMovie(movie4);
        List<Movie> movieList = dbService.getAllMovies();

        //Then
        assertEquals(4, movieList.size());
    }

    @Test
    void shouldDeleteMovie() {
        //Given
        Movie movie1 = new Movie("The Cause", "s get their home back", 2012);

        //When
        dbService.saveMovie(movie1);
        Long movieId = movie1.getId();
        dbService.deleteMovieById(movieId);
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        //Then
        assertFalse(optionalMovie.isPresent());
    }
}
