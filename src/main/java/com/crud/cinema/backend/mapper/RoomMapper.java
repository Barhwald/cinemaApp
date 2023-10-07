package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.service.RoomDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomMapper {

    @Autowired
    private RoomDbService roomDbService;

    public Room mapToRoom(final RoomDto roomDto) {
        return new Room(
                roomDto.getId(),
                roomDto.getName(),
                roomDto.getSeats()
        );
    }

    public RoomDto mapToRoomDto(final Room room) {
        return new RoomDto(
                room.getId(),
                room.getName(),
                room.getSeats(),
                roomDbService.getRoomWithId(room.getId()).getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()),
                roomDbService.getRoomWithId(room.getId()).getPerformances().stream().map(Performance::getId).collect(Collectors.toSet())
        );
    }

    public List<RoomDto> mapToRoomDtoList(final List<Room> rooms) {
        return rooms.stream()
                .map(this::mapToRoomDto)
                .collect(Collectors.toList());
    }
}
