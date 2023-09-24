package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.mapper.RoomMapper;
import com.crud.cinema.backend.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomFacade {

    private final DbService dbService;
    private final RoomMapper roomMapper;

    public List<RoomDto> getRoomsList() {
        List<Room> rooms = dbService.getAllRooms();
        return rooms.stream()
                .map(roomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    public RoomDto getRoomWithId(Long roomId) {
        Room room = dbService.getRoomWithId(roomId);
        return roomMapper.mapToRoomDto(room);
    }

    public void createRoom(final RoomDto roomDto) {
        Room room = roomMapper.mapToRoom(roomDto);
        dbService.saveRoom(room);
    }

    public RoomDto updateRoom(final RoomDto roomDto) {
        Room room = roomMapper.mapToRoom(roomDto);
        Room savedRoom = dbService.saveRoom(room);
        return roomMapper.mapToRoomDto(savedRoom);
    }

    public void deleteRoom(Long roomId) {
        dbService.deleteMovieById(roomId);
    }

}
