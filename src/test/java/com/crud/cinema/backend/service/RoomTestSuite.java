package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.RoomRepository;
import com.crud.cinema.backend.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
public class RoomTestSuite {

    @Autowired
    private DbService dbService;
    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldSaveRoom() {
        //Given
        Room room = new Room("120");

        //When
        dbService.saveRoom(room);
        Long roomId = room.getId();

        //Then
        assertNotEquals(0, roomId);
    }

    @Test
    void shouldGetRoom() {
        //Given
        Room room = new Room("120");

        //When
        dbService.saveRoom(room);
        Room savedRoom = dbService.getRoomWithId(room.getId());

        //Then
        assertEquals(savedRoom.getId(), room.getId());
        assertEquals(savedRoom.getSeats(), room.getSeats());
    }

    @Test
    void shouldGetAllRooms() {
        //Given
        Employee employee1 = new Employee("John", "Wayne");
        Employee employee2 = new Employee("Mary", "Gold");
        Employee employee3 = new Employee("Jack", "Black");

        Set<Employee> setOne = Set.of(employee1, employee2);
        Set<Employee> setTwo = Set.of(employee2, employee3);
        Set<Employee> setThree = Set.of(employee1, employee3);

        Room room1 = new Room("120", setOne);
        Room room2 = new Room("120", setTwo);
        Room room3 = new Room("150", setThree);

        //When
        dbService.saveRoom(room1);
        dbService.saveRoom(room2);
        dbService.saveRoom(room3);
        List<Room> roomsList = dbService.getAllRooms();

        //Then
        assertEquals(3, roomsList.size());
    }

    @Test
    void shouldDeleteRoom() {
        //Given
        Employee employee1 = new Employee("John", "Wayne");
        Employee employee2 = new Employee("Mary", "Gold");

        Set<Employee> setOne = Set.of(employee1, employee2);

        Room room = new Room("80", setOne);

        //When
        dbService.saveRoom(room);
        Long roomId = room.getId();
        dbService.deleteRoomById(roomId);
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        //Then
        assertFalse(optionalRoom.isPresent());
    }

}
