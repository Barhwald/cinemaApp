package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceMapper {

    public Performance mapToPerformance(final PerformanceDto performanceDto) {
        return new Performance(
                performanceDto.getId(),
                performanceDto.getDate(),
                performanceDto.getTime(),
                performanceDto.getMovie(),
                performanceDto.getRoom()
        );
    }

    public PerformanceDto mapToPerformanceDto(final Performance performance) {
        return new PerformanceDto(
                performance.getId(),
                performance.getDate(),
                performance.getTime(),
                performance.getMovie(),
                performance.getRoom()
        );
    }

    public List<PerformanceDto> mapToPerformanceDtoList(final List<Performance> performances) {
        return performances.stream()
                .map(this::mapToPerformanceDto)
                .collect(Collectors.toList());
    }
}
