package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.service.PerformanceDbService;
import com.crud.cinema.backend.service.RoomDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
class PerformanceMapperTest {

    @InjectMocks
    private PerformanceMapper performanceMapper;

    @Mock
    private PerformanceDbService performanceDbService;

    @Test
    public void testMapToPerformance() {
        //Given
        PerformanceDto performanceDto = new PerformanceDto(1L, "2023-10-07", "18:00", 1L, 1L);

        when(performanceDbService.getPerformanceWithId(1L))
                .thenReturn(new Performance(1L, "2023-10-07", "18:00", new Movie(), new Room()));

        //When
        Performance result = performanceMapper.mapToPerformance(performanceDto);

        //Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("2023-10-07", result.getDate());
        assertEquals("18:00", result.getTime());
        verify(performanceDbService, times(2)).getPerformanceWithId(1L);
    }

    @Test
    void shouldMapToPerformanceDto() {
        //Given
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        Room room = new Room(1L, "300");
        Performance performance = new Performance(1L, "10.10.2023",
                "13:45", movie, room);

        //When
        PerformanceDto performanceDto = performanceMapper.mapToPerformanceDto(performance);

        //Then
        assertEquals(1, performanceDto.getId());
        assertEquals("10.10.2023", performanceDto.getDate());
        assertEquals("13:45", performanceDto.getTime());
        assertEquals(1L, performanceDto.getMovieId());
        assertEquals(1L, performanceDto.getRoomId());
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