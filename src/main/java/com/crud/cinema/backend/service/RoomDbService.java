package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomDbService {

    private final RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    public Room getRoomWithId(long id) {
        return roomRepository.findById(id).isPresent() ? roomRepository.findById(id).get() : null;
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public boolean deleteRoomById(long id) {
        roomRepository.deleteById(id);
        return true;
    }

    public Set<Room> getRoomsWithId(String id) {
        return getAllRooms().stream()
                .filter(room -> room.getId().toString().contains(id))
                .collect(Collectors.toSet());
    }

    public Set<Room> getRoomsWithSeats(String seats) {
        return getAllRooms().stream()
                .filter(room -> room.getSeats().contains(seats))
                .collect(Collectors.toSet());
    }

    public Set<Room> getRoomsWithEmployees(String searchString) {
        return getAllRooms().stream()
                .filter(room -> room.getEmployees()
                        .stream()
                        .anyMatch(emp ->
                                emp.getFirstName().toLowerCase().contains(searchString.toLowerCase()) ||
                                        emp.getLastName().toLowerCase().contains(searchString.toLowerCase())))
                .collect(Collectors.toSet());
    }
}
