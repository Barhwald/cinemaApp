package com.crud.cinema.backend.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PERFORMANCES")
@ToString
public class Performance {

    @Id
    @GeneratedValue
    @Column(name = "PERFORMANCE_ID", unique = true)
    private Long id;

    @Column(name = "DATE")
    private String date;

    @Column(name = "TIME")
    private String time;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public Performance(String date, String time, Movie movie, Room room) {
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.room = room;
    }
}



