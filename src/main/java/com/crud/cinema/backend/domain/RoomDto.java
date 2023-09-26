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
    private String seats;
    private Set<Employee> employees;
    private List<Performance> performances;

    public RoomDto(Long id, String seats) {
        this.id = id;
        this.seats = seats;
    }

}
