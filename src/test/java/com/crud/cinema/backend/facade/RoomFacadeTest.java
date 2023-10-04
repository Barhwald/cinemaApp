package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.mapper.RoomMapper;
import com.crud.cinema.backend.service.RoomDbService;
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
class RoomFacadeTest {
    @InjectMocks
    private RoomFacade roomFacade;
    @Mock
    private RoomDbService roomDbService;
    @Mock
    private RoomMapper roomMapper;

    @Test
    void shouldGetRoomList() {
        //Given
        Room room1 = new Room(1L, "Big1", "3000");
        Room room2 = new Room(2L, "Big2", "3000");
        List<Room> roomList = List.of(room1, room2);

        when(roomDbService.getAllRooms()).thenReturn(roomList);

        //When
        List<RoomDto> roomDtoList = roomFacade.getRoomsList();

        //Then
        assertEquals(2, roomDtoList.size());
    }

    @Test
    void shouldGetRoomWithId() {
        //Given
        Room room1 = new Room(1L, "Big1", "3000");
        RoomDto roomDto1 = new RoomDto(1L, "Big1", "3000");

        when(roomDbService.getRoomWithId(1L)).thenReturn(room1);
        when(roomMapper.mapToRoomDto(room1)).thenReturn(roomDto1);

        //When
        RoomDto resultRoomDto = roomFacade.getRoomWithId(1L);

        //Then
        assertEquals("Big1", resultRoomDto.getName());
        assertEquals("3000", resultRoomDto.getSeats());
    }

    @Test
    void shouldCreateRoom() {
        //Given
        Room room1 = new Room(1L, "Big1", "3000");
        RoomDto roomDto1 = new RoomDto(1L, "Big1", "3000");

        when(roomMapper.mapToRoom(roomDto1)).thenReturn(room1);

        //When
        roomFacade.createRoom(roomDto1);

        //Then
        verify(roomMapper).mapToRoom(roomDto1);
        verify(roomDbService).saveRoom(room1);
    }

    @Test
    void shouldUpdateRoom() {
        //Given
        RoomDto roomDto1 = new RoomDto(1L, "Big1", "3000");
        Room mappedRoom = new Room(1L, "Big1", "3000");
        Room savedRoom = new Room(1L, "Big1", "3000");

        when(roomMapper.mapToRoom(roomDto1)).thenReturn(mappedRoom);
        when(roomDbService.saveRoom(mappedRoom)).thenReturn(savedRoom);
        when(roomMapper.mapToRoomDto(savedRoom)).thenReturn(roomDto1);

        //When
        RoomDto updatedRoomDto = roomFacade.updateRoom(roomDto1);

        //Then
        verify(roomMapper).mapToRoom(roomDto1);
        verify(roomDbService).saveRoom(mappedRoom);
        verify(roomMapper).mapToRoomDto(savedRoom);
        assertEquals(roomDto1, updatedRoomDto);
    }

    @Test
    void shouldDeletePerformance() {
        //Given
        Long roomId = 1L;

        //When
        roomFacade.deleteRoom(roomId);

        //Then
        verify(roomDbService).deleteRoomById(roomId);
    }
}