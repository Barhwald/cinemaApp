package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private String seats;
    private Set<Long> employeeIds;
    private Set<Long> performanceIds;

    public RoomDto(Long id, String seats) {
        this.id = id;
        this.seats = seats;
    }

    public RoomDto(Long id, String name, String seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }
}
