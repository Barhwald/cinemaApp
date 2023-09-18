package com.crud.cinema.backend.repository;

import com.crud.cinema.backend.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Override
    List<Movie> findAll();

}
