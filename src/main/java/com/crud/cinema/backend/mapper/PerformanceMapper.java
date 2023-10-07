package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.service.PerformanceDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceMapper {

    @Autowired
    private PerformanceDbService performanceDbService;

    public Performance mapToPerformance(final PerformanceDto performanceDto) {
        return new Performance(
                performanceDto.getId(),
                performanceDto.getDate(),
                performanceDto.getTime(),
                performanceDbService.getPerformanceWithId(performanceDto.getId()).getMovie(),
                performanceDbService.getPerformanceWithId(performanceDto.getId()).getRoom()
        );
    }

    public PerformanceDto mapToPerformanceDto(final Performance performance) {
        return new PerformanceDto(
                performance.getId(),
                performance.getDate(),
                performance.getTime(),
                performance.getMovie().getId(),
                performance.getRoom().getId()
        );
    }

    public List<PerformanceDto> mapToPerformanceDtoList(final List<Performance> performances) {
        return performances.stream()
                .map(this::mapToPerformanceDto)
                .collect(Collectors.toList());
    }
}
