package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.mapper.PerformanceMapper;
import com.crud.cinema.backend.service.PerformanceDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class PerformanceFacadeTest {
    @InjectMocks
    private PerformanceFacade performanceFacade;
    @Mock
    private PerformanceDbService performanceDbService;
    @Mock
    private PerformanceMapper performanceMapper;

    @Test
    void shouldGetPerformanceList() {
        //Given
        Performance performance1 = new Performance();
        Performance performance2 = new Performance();
        List<Performance> performanceList = List.of(performance1, performance2);

        when(performanceDbService.getAllPerformances()).thenReturn(performanceList);

        //When
        List<PerformanceDto> performanceDtoList = performanceFacade.getPerformancesList();

        //Then
        assertEquals(2, performanceDtoList.size());
    }

    @Test
    void shouldGetPerformanceWithId() {
        //Given
        Performance performance1 = new Performance(1L, "10.10.2023", "10:30", new Movie(), new Room());
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "10.10.2023", "10:30", new Movie(), new Room());

        when(performanceDbService.getPerformanceWithId(1L)).thenReturn(performance1);
        when(performanceMapper.mapToPerformanceDto(performance1)).thenReturn(performanceDto1);

        //When
        PerformanceDto resultPerformanceDto = performanceFacade.getPerformanceWithId(1L);

        //Then
        assertEquals("10.10.2023", resultPerformanceDto.getDate());
        assertEquals("10:30", resultPerformanceDto.getTime());
        assertEquals(new Room(), resultPerformanceDto.getRoom());
        assertEquals(new Movie(), resultPerformanceDto.getMovie());
    }

    @Test
    void shouldCreatePerformance() {
        //Given
        Performance performance1 = new Performance(1L, "10.10.2023", "10:30", new Movie(), new Room());
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "10.10.2023", "10:30", new Movie(), new Room());

        when(performanceMapper.mapToPerformance(performanceDto1)).thenReturn(performance1);

        //When
        performanceFacade.createPerformance(performanceDto1);

        //Then
        verify(performanceMapper).mapToPerformance(performanceDto1);
        verify(performanceDbService).savePerformance(performance1);
    }

    @Test
    void shouldUpdatePerformance() {
        //Given
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "10.10.2023", "10:30", new Movie(), new Room());
        Performance mappedPerformance = new Performance(1L, "10.10.2023", "10:30", new Movie(), new Room());
        Performance savedPerformance = new Performance(1L, "10.10.2023", "10:30", new Movie(), new Room());

        when(performanceMapper.mapToPerformance(performanceDto1)).thenReturn(mappedPerformance);
        when(performanceDbService.savePerformance(mappedPerformance)).thenReturn(savedPerformance);
        when(performanceMapper.mapToPerformanceDto(savedPerformance)).thenReturn(performanceDto1);

        //When
        PerformanceDto updatedPerformanceDto = performanceFacade.updatePerformance(performanceDto1);

        //Then
        verify(performanceMapper).mapToPerformance(performanceDto1);
        verify(performanceDbService).savePerformance(mappedPerformance);
        verify(performanceMapper).mapToPerformanceDto(savedPerformance);
        assertEquals(performanceDto1, updatedPerformanceDto);
    }

    @Test
    void shouldDeletePerformance() {
        //Given
        Long performanceId = 1L;

        //When
        performanceFacade.deletePerformance(performanceId);

        //Then
        verify(performanceDbService).deletePerformanceById(performanceId);

    }
}