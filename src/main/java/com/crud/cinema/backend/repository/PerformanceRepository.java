package com.crud.cinema.backend.repository;

import com.crud.cinema.backend.domain.Performance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PerformanceRepository extends CrudRepository<Performance, Long> {
    @Override
    List<Performance> findAll();

}
