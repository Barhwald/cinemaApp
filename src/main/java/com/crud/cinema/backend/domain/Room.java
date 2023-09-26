package com.crud.cinema.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(exclude="employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ROOMS")
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID", unique = true)
    private Long id;

    @Column(name = "SEATS")
    private String seats;

    @ManyToMany(mappedBy = "rooms",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    public Set<Employee> employees = new HashSet<>();

    @OneToMany(
            targetEntity = Performance.class,
            mappedBy = "room",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Performance> performances = new ArrayList<>();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getRooms().add(this);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getRooms().add(this);
    }

    public Room(String seats) {
        this.seats = seats;
    }

    public Room(Long id, String seats) {
        this.id = id;
        this.seats = seats;
    }

    public Room(String seats, Set<Employee> employees) {
        this.seats = seats;
        this.employees = employees;
    }

}
