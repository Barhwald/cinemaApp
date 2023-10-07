package com.crud.cinema.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(exclude = {"employees", "performances"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ROOMS")
@ToString(exclude = {"performances"})
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID", unique = true)
    private Long id;

    @Column(name = "ROOM_NAME")
    private String name;

    @Column(name = "ROOM_SEATS")
    private String seats;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "JOIN_ROOM_EMPLOYEE",
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")},
            joinColumns = {@JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ROOM_ID", "EMPLOYEE_ID"})}
    )
    public Set<Employee> employees = new HashSet<>();

    @OneToMany(
            targetEntity = Performance.class,
            mappedBy = "room",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    private Set<Performance> performances = new HashSet<>();

    public void addPerformance(Performance performance) {
        performances.add(performance);
        performance.setRoom(this);
    }

    @PreRemove
    public void removePerformanceAssociations() {
        for (Performance performance : this.performances) {
            performance.setRoom(null);
        }
    }

    public void removePerformance(Performance performance) {
        performances.remove(performance);
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getRooms().add(this);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getRooms().remove(this);
    }

    public Room(String seats) {
        this.seats = seats;
    }

    public Room(Long id, String seats) {
        this.id = id;
        this.seats = seats;
    }

    public Room(Long id, String name, String seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public Room(String seats, Set<Employee> employees) {
        this.seats = seats;
        this.employees = employees;
    }

    public Room(String name, String seats, Set<Employee> employees) {
        this.name = name;
        this.seats = seats;
        this.employees = employees;
    }
}
