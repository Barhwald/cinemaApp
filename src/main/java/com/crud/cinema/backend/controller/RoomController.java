package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.facade.RoomFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomFacade roomFacade;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getRooms() {
        return ResponseEntity.ok(roomFacade.getRoomsList());
    }

    @GetMapping(value = "{roomId}")
    public ResponseEntity<RoomDto> getRoomWithId(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomFacade.getRoomWithId(roomId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRoom(@RequestBody RoomDto roomDto) {
        roomFacade.createRoom(roomDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<RoomDto> updateRoom(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomFacade.updateRoom(roomDto));
    }

    @DeleteMapping(value = "{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomFacade.deleteRoom(roomId);
        return ResponseEntity.ok().build();
    }
}
