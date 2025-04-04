package com.crud.cinema.backend.domain;

import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public Performance(String date, String time, Movie movie, Room room) {
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.room = room;
    }
}



