package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomMapperTest {

    @Autowired
    private RoomMapper roomMapper;

    @Test
    void shouldMapToRoom() {
        //Given
        RoomDto roomDto = new RoomDto(1L, 120);

        //When
        Room room = roomMapper.mapToRoom(roomDto);

        //Then
        assertEquals(1L, room.getId());
        assertEquals(120, room.getSeats());
    }

    @Test
    void shouldMapToRoomDto() {
        //Given
        Room room = new Room(1L, 180);

        //When
        RoomDto roomDto = roomMapper.mapToRoomDto(room);

        //Then
        assertEquals(1L, roomDto.getId());
        assertEquals(180, roomDto.getSeats());
    }

    @Test
    void shouldMapToRoomDtoList() {
        //Given
        Room room1 = new Room(1L, 180);
        Room room2 = new Room(2L, 180);
        Room room3 = new Room(3L, 180);
        Room room4 = new Room(4L, 180);
        List<Room> roomList = List.of(room1, room2, room3, room4);

        //When
        List<RoomDto> roomDtoList = roomMapper.mapToRoomDtoList(roomList);

        //Then
        assertEquals(4, roomDtoList.size());
        assertEquals(1L, roomDtoList.get(0).getId());
        assertEquals(2L, roomDtoList.get(1).getId());
        assertEquals(3L, roomDtoList.get(2).getId());
        assertEquals(4L, roomDtoList.get(3).getId());

    }
}