package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieDbService {

    private final MovieRepository movieRepository;

    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
    }
    public Movie getMovieWithId(long id) {
        return movieRepository.findById(id).isPresent() ? movieRepository.findById(id).get() : null;
    }
    public Set<Movie> getMoviesWithId(String id) {
        return getAllMovies().stream()
                .filter(mov -> mov.getId().toString().contains(id))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithTitle(String title) {
        String lowerCaseTitle = title.toLowerCase();
        return getAllMovies().stream()
                .filter(mov -> mov.getTitle().toLowerCase().contains(lowerCaseTitle))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithDescription(String desc) {
        String lowerCaseDesc = desc.toLowerCase();
        return getAllMovies().stream()
                .filter(mov -> mov.getDescription().toLowerCase().contains(lowerCaseDesc))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithYear(String year) {
        return getAllMovies().stream()
                .filter(mov -> mov.getYear().contains(year))
                .collect(Collectors.toSet());
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public boolean deleteMovieById(long id) {
        movieRepository.deleteById(id);
        return true;
    }

}
