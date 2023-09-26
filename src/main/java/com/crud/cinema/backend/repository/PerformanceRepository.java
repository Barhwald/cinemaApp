package com.crud.cinema.backend.repository;

import com.crud.cinema.backend.domain.Performance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends CrudRepository<Performance, Long> {
    @Override
    List<Performance> findAll();

}
