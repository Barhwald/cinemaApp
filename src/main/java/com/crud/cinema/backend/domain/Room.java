package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ROOMS")
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID", unique = true)
    private Long id;

    @Column(name = "SEATS")
    private Integer seats;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "rooms")
    public List<Employee> employees = new ArrayList<>();

    @OneToMany(
            targetEntity = Performance.class,
            mappedBy = "room",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Performance> performances = new ArrayList<>();

    public Room(Integer seats) {
        this.seats = seats;
    }

    public Room(Long id, Integer seats) {
        this.id = id;
        this.seats = seats;
    }

    public Room(Integer seats, List<Employee> employees) {
        this.seats = seats;
        this.employees = employees;
    }
}
