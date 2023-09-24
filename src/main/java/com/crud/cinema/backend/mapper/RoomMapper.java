package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomMapper {

    public Room mapToRoom(final RoomDto roomDto) {
        return new Room(
                roomDto.getId(),
                roomDto.getSeats()
        );
    }

    public RoomDto mapToRoomDto(final Room room) {
        return new RoomDto(
                room.getId(),
                room.getSeats(),
                room.getEmployees(),
                room.getPerformances()
        );
    }

    public List<RoomDto> mapToRoomDtoList(final List<Room> rooms) {
        return rooms.stream()
                .map(this::mapToRoomDto)
                .collect(Collectors.toList());
    }
}
