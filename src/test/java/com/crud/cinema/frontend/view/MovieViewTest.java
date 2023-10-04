package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.service.MovieDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
public class MovieViewTest {

    @InjectMocks
    private MovieView movieView;

    @Mock
    private MovieDbService movieDbService;

    @Test
    void shouldSetColumnsProperly() {
        //Given

        //When & Then
        assertEquals("id", movieView.getMovieGrid().getColumns().get(0).getKey());
        assertEquals("title", movieView.getMovieGrid().getColumns().get(1).getKey());
        assertEquals("description", movieView.getMovieGrid().getColumns().get(2).getKey());
        assertEquals("year", movieView.getMovieGrid().getColumns().get(3).getKey());
    }

    @Test
    void shouldFilterMovieById() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        when(movieDbService.getMoviesWithId("1")).thenReturn(Set.of(movie));

        //When
        movieView.getFilter1().setValue("1");

        //Then
        assertEquals(1, movieDbService.getMoviesWithId(movieView.getFilter1().getValue()).size());
    }

    @Test
    void shouldFilterMovieByTitle() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        when(movieDbService.getMoviesWithTitle("Title")).thenReturn(Set.of(movie));

        //When
        movieView.getFilter2().setValue("Title");

        //Then
        assertEquals(1, movieDbService.getMoviesWithTitle(movieView.getFilter2().getValue()).size());
    }

    @Test
    void shouldFilterMovieByDescription() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        when(movieDbService.getMoviesWithDescription("Desc")).thenReturn(Set.of(movie));

        //When
        movieView.getFilter3().setValue("Desc");

        //Then
        assertEquals(1, movieDbService.getMoviesWithDescription(movieView.getFilter3().getValue()).size());
    }

    @Test
    void shouldFilterMovieByYear() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        when(movieDbService.getMoviesWithYear("2002")).thenReturn(Set.of(movie));

        //When
        movieView.getFilter4().setValue("2002");

        //Then
        assertEquals(1, movieDbService.getMoviesWithYear(movieView.getFilter4().getValue()).size());
    }
}