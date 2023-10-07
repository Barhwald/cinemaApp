package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.service.RoomDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoomMapperTest {

    @InjectMocks
    private RoomMapper roomMapper;

    @Mock
    private RoomDbService roomDbService;

    @Test
    void shouldMapToRoom() {
        //Given
        RoomDto roomDto = new RoomDto(1L, "120");

        //When
        Room room = roomMapper.mapToRoom(roomDto);

        //Then
        assertEquals(1L, room.getId());
        assertEquals("120", room.getSeats());
    }

    @Test
    void shouldMapToRoomDto() {
        //Given
        Room room = new Room(1L, "180");
        when(roomDbService.getRoomWithId(1L)).thenReturn(room);

        //When
        RoomDto roomDto = roomMapper.mapToRoomDto(room);

        //Then
        assertEquals(1L, roomDto.getId());
        assertEquals("180", roomDto.getSeats());
    }

    @Test
    void shouldMapToRoomDtoList() {
        //Given
        Room room1 = new Room(1L, "180");
        Room room2 = new Room(2L, "180");
        Room room3 = new Room(3L, "180");
        Room room4 = new Room(4L, "180");
        List<Room> roomList = List.of(room1, room2, room3, room4);

        when(roomDbService.getRoomWithId(1L)).thenReturn(room1);
        when(roomDbService.getRoomWithId(2L)).thenReturn(room2);
        when(roomDbService.getRoomWithId(3L)).thenReturn(room3);
        when(roomDbService.getRoomWithId(4L)).thenReturn(room4);

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