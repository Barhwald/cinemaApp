package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.EmployeeDbService;
import com.crud.cinema.backend.service.RoomDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class RoomViewTest {

    @InjectMocks
    private RoomView roomView;

    @Mock
    private RoomDbService roomDbService;

    @Mock
    private EmployeeDbService employeeDbService;

    @Test
    public void shouldSetColumnsProperly() {
        //Given

        //When & Then
        assertEquals("id", roomView.getRoomGrid().getColumns().get(0).getKey());
        assertEquals("name", roomView.getRoomGrid().getColumns().get(1).getKey());
        assertEquals("seats", roomView.getRoomGrid().getColumns().get(2).getKey());
    }

    @Test
    void shouldFilterRoomById() {
        //Given
        Room room = new Room(1L, "Big", "1000");
        when(roomDbService.getRoomsWithId("1")).thenReturn(Set.of(room));

        //When
        roomView.getFilter1().setValue("1");

        //Then
        assertEquals(1, roomDbService.getRoomsWithId(roomView.getFilter1().getValue()).size());
    }

    @Test
    void shouldFilterRoomByName() {
        //Given
        Room room = new Room(1L, "Big", "1000");
        when(roomDbService.getRoomsWithName("Big")).thenReturn(Set.of(room));

        //When
        roomView.getFilter2().setValue("Big");

        //Then
        assertEquals(1, roomDbService.getRoomsWithName(roomView.getFilter2().getValue()).size());
    }

    @Test
    void shouldFilterRoomBySeats() {
        //Given
        Room room = new Room(1L, "Big", "1000");
        when(roomDbService.getRoomsWithSeats("1000")).thenReturn(Set.of(room));

        ///When
        roomView.getFilter3().setValue("1000");

        //Then
        assertEquals(1, roomDbService.getRoomsWithSeats(roomView.getFilter3().getValue()).size());
    }

    @Test
    void shouldFilterRoomByEmployees() {
        //Given
        Room room = new Room("Big", "1000", Set.of(new Employee("Mike", "Drough")));
        when(roomDbService.getRoomsWithEmployees("Mike")).thenReturn(Set.of(room));

        //When
        roomView.getFilter4().setValue("Mike");

        //Then
        assertEquals(1, roomDbService.getRoomsWithEmployees(roomView.getFilter4().getValue()).size());

    }
}