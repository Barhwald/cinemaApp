package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceDbService {

    private final PerformanceRepository performanceRepository;


    public Performance savePerformance(final Performance performance) {
        return performanceRepository.save(performance);
    }
    public Performance getPerformanceWithId(long id) {
        return performanceRepository.findById(id).isPresent() ? performanceRepository.findById(id).get() : null;
    }
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }
    public void deletePerformanceById(long id) {
        performanceRepository.deleteById(id);
    }

}
