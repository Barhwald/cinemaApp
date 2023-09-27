package com.crud.cinema.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "EMPLOYEE_ID", unique = true)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "JOIN_ROOM_EMPLOYEE",
            joinColumns = {@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ROOM_ID", "EMPLOYEE_ID"})}
    )
    private Set<Room> rooms = new HashSet<>();

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName, Set<Room> rooms) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rooms = rooms;
    }

    public Employee(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }



}
