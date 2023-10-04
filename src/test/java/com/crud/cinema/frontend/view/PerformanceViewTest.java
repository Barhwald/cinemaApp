package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.service.PerformanceDbService;
import com.crud.cinema.backend.service.RoomDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class PerformanceViewTest {

    @Mock
    private PerformanceDbService performanceDbService;

    @Mock
    private MovieDbService movieDbService;

    @Mock
    private RoomDbService roomDbService;

    @InjectMocks
    private PerformanceView performanceView;

    @Test
    public void shouldSetColumnsProperly() {
        //Given

        //When & Then
        assertEquals("id", performanceView.getPerformanceGrid().getColumns().get(0).getKey());
    }

    @Test
    void shouldFilterPerformanceById() {
        //Given
        Performance performance = new Performance(1L, "10.10.2023", "10:00", new Movie(), new Room());
        when(performanceDbService.getPerformancesWithId("1")).thenReturn(Set.of(performance));

        //When
        performanceView.getFilter1().setValue("1");

        //Then
        assertEquals(1, performanceDbService.getPerformancesWithId(performanceView.getFilter1().getValue()).size());
    }

    @Test
    void shouldFilterPerformanceByMovieTitle() {
        //Given
        Performance performance = new Performance(1L, "10.10.2023", "10:00", new Movie("Title", "Desc", "2002"), new Room());
        when(performanceDbService.getPerformancesWithMovie("Title")).thenReturn(Set.of(performance));

        //When
        performanceView.getFilter2().setValue("Title");

        //Then
        assertEquals(1, performanceDbService.getPerformancesWithMovie(performanceView.getFilter2().getValue()).size());

    }

    @Test
    void shouldFilterPerformanceByRoomName() {
        //Given
        Performance performance = new Performance(1L, "10.10.2023", "10:00", new Movie("Title", "Desc", "2002"), new Room(1L, "Big", "300"));
        when(performanceDbService.getPerformancesWithRoom("Big")).thenReturn(Set.of(performance));

        //When
        performanceView.getFilter3().setValue("Big");

        //Then
        assertEquals(1, performanceDbService.getPerformancesWithRoom(performanceView.getFilter3().getValue()).size());
    }

    @Test
    void shouldFilterPerformanceByDate() {
        Performance performance = new Performance(1L, "10.10.2023", "10:00", new Movie(), new Room());
        when(performanceDbService.getPerformancesWithDate("10.10.2023")).thenReturn(Set.of(performance));

        //When
        performanceView.getFilter4().setValue("10.10.2023");

        //Then
        assertEquals(1, performanceDbService.getPerformancesWithDate(performanceView.getFilter4().getValue()).size());
    }

    @Test
    void shouldFilterPerformanceByTime() {
        Performance performance = new Performance(1L, "10.10.2023", "10:00", new Movie(), new Room());
        when(performanceDbService.getPerformancesWithTime("10:00")).thenReturn(Set.of(performance));

        //When
        performanceView.getFilter5().setValue("10:00");

        //Then
        assertEquals(1, performanceDbService.getPerformancesWithTime(performanceView.getFilter5().getValue()).size());
    }
}
