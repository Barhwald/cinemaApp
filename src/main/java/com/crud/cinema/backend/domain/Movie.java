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
@Entity(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "MOVIE_ID", unique = true)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "YEAR")
    private int year;

    @OneToMany(
            targetEntity = Performance.class,
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Performance> performances = new ArrayList<>();


}
