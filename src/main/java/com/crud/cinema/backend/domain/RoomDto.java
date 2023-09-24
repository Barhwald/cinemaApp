package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private Integer seats;
    private List<Employee> employees;
    private List<Performance> performances;

    public RoomDto(Long id, Integer seats) {
        this.id = id;
        this.seats = seats;
    }

}
