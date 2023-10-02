package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.mapper.PerformanceMapper;
import com.crud.cinema.backend.service.PerformanceDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PerformanceFacade {

    private final PerformanceDbService performanceDbService;
    private final PerformanceMapper performanceMapper;

    public List<PerformanceDto> getPerformancesList() {
        List<Performance> performances = performanceDbService.getAllPerformances();
        return performances.stream()
                .map(performanceMapper::mapToPerformanceDto)
                .collect(Collectors.toList());
    }

    public PerformanceDto getPerformanceWithId(Long performanceId) {
        Performance performance = performanceDbService.getPerformanceWithId(performanceId);
        return performanceMapper.mapToPerformanceDto(performance);
    }

    public void createPerformance(final PerformanceDto performanceDto) {
        Performance performance = performanceMapper.mapToPerformance(performanceDto);
        performanceDbService.savePerformance(performance);
    }

    public PerformanceDto updatePerformance(final PerformanceDto performanceDto) {
        Performance performance = performanceMapper.mapToPerformance(performanceDto);
        Performance savedPerformance = performanceDbService.savePerformance(performance);
        return performanceMapper.mapToPerformanceDto(savedPerformance);
    }

    public void deletePerformance(Long performanceId) {
        performanceDbService.deletePerformanceById(performanceId);
    }
}
