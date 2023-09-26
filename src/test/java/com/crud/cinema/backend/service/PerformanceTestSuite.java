package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.PerformanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class PerformanceTestSuite {

    @Autowired
    private DbService dbService;
    @Autowired
    private PerformanceRepository performanceRepository;

    @Test
    void shouldSavePerformance() {
        //Given
        Employee employee1 = new Employee("Harry", "Bleach");
        Employee employee2 = new Employee("Harry", "Kane");
        Movie movie = new Movie("The Hill", "Yadadadadada", "1994");
        Room room = new Room("120", Set.of(employee1, employee2));

        Performance performance = new Performance(LocalDateTime.now(), movie, room);

        //When
        dbService.savePerformance(performance);
        Long perfId = performance.getId();

        //Then
        assertNotEquals(0, perfId);
    }

    @Test
    void shouldGetPerformance() {
        //Given
        Employee employee1 = new Employee("Harry", "Bleach");
        Employee employee2 = new Employee("Harry", "Kane");
        Movie movie = new Movie("The Hill", "Yadadadadada", "1994");
        Room room = new Room("120", Set.of(employee1, employee2));

        Performance performance = new Performance(LocalDateTime.now(), movie, room);

        //When
        dbService.savePerformance(performance);
        Performance savedPerformance = dbService.getPerformanceWithId(performance.getId());

        //Then
        assertEquals(savedPerformance.getId(), performance.getId());
        assertEquals(savedPerformance.getDateTime(), performance.getDateTime());
        assertEquals(savedPerformance.getMovie(), performance.getMovie());
        assertEquals(savedPerformance.getRoom(), performance.getRoom());
    }

    @Test
    void shouldGetAllPerformances() {
        //Given
        Employee employee1 = new Employee("Harry", "Bleach");
        Employee employee2 = new Employee("Harry", "Kane");
        Movie movie = new Movie("The Hill", "Yadadadadada", "1994");
        Room room1 = new Room("120", Set.of(employee1, employee2));
        Room room2 = new Room("78", Set.of(employee2));

        Performance performance1 = new Performance(LocalDateTime.now(), movie, room1);
        Performance performance2 = new Performance(LocalDateTime.now(), movie, room2);

        //When
        dbService.savePerformance(performance1);
        dbService.savePerformance(performance2);
        List<Performance> perfList = dbService.getAllPerformances();

        //Then
        assertEquals(2, perfList.size());

    }

    @Test
    void shouldDeletePerformance() {
        //Given
        Employee employee1 = new Employee("Harry", "Bleach");
        Employee employee2 = new Employee("Harry", "Kane");
        Movie movie = new Movie("The Hill", "Yadadadadada", "1994");
        Room room = new Room("120", Set.of(employee1, employee2));

        Performance performance = new Performance(LocalDateTime.now(), movie, room);

        //When
        dbService.savePerformance(performance);
        Long perfId = performance.getId();
        dbService.deletePerformanceById(perfId);
        Optional<Performance> optionalPerformance = performanceRepository.findById(perfId);

        //Then
        assertFalse(optionalPerformance.isPresent());
    }
}
