package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.domain.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PerformanceMapperTest {

    @Autowired
    private PerformanceMapper performanceMapper;

    @Test
    void shouldMapToPerformance() {
        //Given
        PerformanceDto performanceDto = new PerformanceDto(1L, "10.10.2023",
                "13:45", new Movie(), new Room());

        //When
        Performance performance = performanceMapper.mapToPerformance(performanceDto);

        //Then
        assertEquals(1, performance.getId());
        assertEquals("10.10.2023", performance.getDate());
        assertEquals("13:45", performance.getTime());
        assertEquals(new Movie(), performance.getMovie());
        assertEquals(new Room(), performance.getRoom());
    }

    @Test
    void shouldMapToPerformanceDto() {
        //Given
        Performance performance = new Performance(1L, "10.10.2023",
                "13:45", new Movie(), new Room());

        //When
        PerformanceDto performanceDto = performanceMapper.mapToPerformanceDto(performance);

        //Then
        assertEquals(1, performanceDto.getId());
        assertEquals("10.10.2023", performanceDto.getDate());
        assertEquals("13:45", performanceDto.getTime());
        assertEquals(new Movie(), performanceDto.getMovie());
        assertEquals(new Room(), performanceDto.getRoom());
    }

    @Test
    void shouldMapToPerformanceDtoList() {
        //Given
        Performance performance1 = new Performance(1L, "10.10.2023",
                "13:45", new Movie(), new Room());
        Performance performance2 = new Performance(2L, "11.10.2023",
                "13:45", new Movie(), new Room());
        List<Performance> performances = List.of(performance1, performance2);

        //When
        List<PerformanceDto> performanceDtoList = performanceMapper.mapToPerformanceDtoList(performances);

        //Then
        assertEquals(2, performanceDtoList.size());
        assertEquals(1, performanceDtoList.get(0).getId());
        assertEquals(2, performanceDtoList.get(1).getId());
    }
}