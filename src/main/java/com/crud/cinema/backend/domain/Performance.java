package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PERFORMANCES")
public class Performance {

    @Id
    @GeneratedValue
    @Column(name = "PERFORMANCE_ID", unique = true)
    private Long id;

    @Column(name = "DATE_TIME")
    public LocalDateTime dateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public Performance(LocalDateTime dateTime, Movie movie, Room room) {
        this.dateTime = dateTime;
        this.movie = movie;
        this.room = room;
    }
}
