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
        Room room = new Room(120);

        //When
        dbService.saveRoom(room);
        Long roomId = room.getId();

        //Then
        assertNotEquals(0, roomId);
    }

    @Test
    void shouldGetRoom() {
        //Given
        Room room = new Room(120);

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

        List<Employee> listOne = List.of(employee1, employee2);
        List<Employee> listTwo = List.of(employee2, employee3);
        List<Employee> listThree = List.of(employee1, employee3);

        Room room1 = new Room(120, listOne);
        Room room2 = new Room(120, listTwo);
        Room room3 = new Room(150, listThree);

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

        List<Employee> listOne = List.of(employee1, employee2);

        Room room = new Room(80, listOne);

        //When
        dbService.saveRoom(room);
        Long roomId = room.getId();
        dbService.deleteRoomById(roomId);
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        //Then
        assertFalse(optionalRoom.isPresent());
    }

}
