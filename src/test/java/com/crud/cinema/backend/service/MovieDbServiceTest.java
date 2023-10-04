package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MovieDbServiceTest {

    @Autowired
    private MovieDbService movieDbService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void shouldSaveMovie() {
        //Given
        Movie movie = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", "2016");

        //When
        movieDbService.saveMovie(movie);
        Long movieId = movie.getId();

        //Then
        assertNotEquals(0, movieId);
    }

    @Test
    void shouldGetMovie() {
        //Given
        Movie movie = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", "2016");

        //When
        movieDbService.saveMovie(movie);
        Movie savedMovie = movieDbService.getMovieWithId(movie.getId());

        //Then
        assertEquals(savedMovie.getId(), movie.getId());
        assertEquals(savedMovie.getDescription(), movie.getDescription());
        assertEquals(savedMovie.getYear(), movie.getYear());
    }

    @Test
    void shouldGetAllMovies() {
        //Given
        Movie movie1 = new Movie("The Cause", "s get their home back", "2012");
        Movie movie2 = new Movie("The Bread", "A small crearves get their home back", "2011");
        Movie movie3 = new Movie("The Duck", "A small creature helps sir home back", "2013");
        Movie movie4 = new Movie("The Hobbit", "A small creature helps some dwarves get their home back", "2018");

        //When
        movieDbService.saveMovie(movie1);
        movieDbService.saveMovie(movie2);
        movieDbService.saveMovie(movie3);
        movieDbService.saveMovie(movie4);
        List<Movie> movieList = movieDbService.getAllMovies();

        //Then
        assertEquals(4, movieList.size());
    }

    @Test
    void shouldDeleteMovie() {
        //Given
        Movie movie1 = new Movie("The Cause", "s get their home back", "2012");

        //When
        movieDbService.saveMovie(movie1);
        Long movieId = movie1.getId();
        movieDbService.deleteMovieById(movieId);
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        //Then
        assertFalse(optionalMovie.isPresent());
    }

    @Test
    void getMoviesWithTitle() {
        //Given
        Movie movie1 = new Movie(1L, "The Cause", "s get their home back", "2012");
        Movie movie2 = new Movie(2L, "The Cheese", "s get their home back", "2012");
        Movie movie11 = new Movie(11L, "The Choose", "s get their home back", "2012");

        //When
        movieDbService.saveMovie(movie1);
        movieDbService.saveMovie(movie2);
        movieDbService.saveMovie(movie11);
        Set<Movie> movieSet = movieDbService.getMoviesWithTitle("The Ch");

        //Then
        assertEquals(2, movieSet.size());
    }

    @Test
    void getMoviesWithDescription() {
        //Given
        Movie movie1 = new Movie(1L, "The Cause", "s get their home back", "2012");
        Movie movie2 = new Movie(2L, "The Cheese", "s get their home back", "2012");
        Movie movie11 = new Movie(11L, "The Choose", "s get their home back", "2012");

        //When
        movieDbService.saveMovie(movie1);
        movieDbService.saveMovie(movie2);
        movieDbService.saveMovie(movie11);
        Set<Movie> movieSet = movieDbService.getMoviesWithDescription("their");
        System.out.println(movieSet.toString());

        //Then
        assertEquals(3, movieSet.size());
    }

    @Test
    void getMoviesWithYear() {
        //Given
        Movie movie1 = new Movie(1L, "The Cause", "s get their home back", "2012");
        Movie movie2 = new Movie(2L, "The Cheese", "s get their home back", "2012");
        Movie movie11 = new Movie(11L, "The Choose", "s get their home back", "2011");

        //When
        movieDbService.saveMovie(movie1);
        movieDbService.saveMovie(movie2);
        movieDbService.saveMovie(movie11);
        Set<Movie> movieSet = movieDbService.getMoviesWithYear("2012");

        //Then
        assertEquals(2, movieSet.size());
    }
}
