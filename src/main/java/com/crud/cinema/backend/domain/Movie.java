package com.crud.cinema.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "performances")
@ToString(exclude = {"performances"})
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
    private String year;

    @OneToMany(
            targetEntity = Performance.class,
            mappedBy = "movie",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    private Set<Performance> performances = new HashSet<>();

    public void addPerformance(Performance performance) {
        performances.add(performance);
        performance.setMovie(this);
    }

    public void removePerformance(Performance performance) {
        this.performances.remove(performance);
    }

    @PreRemove
    public void removePerformanceAssociations() {
        for (Performance performance : this.performances) {
            performance.setMovie(null);
        }
    }

    public Movie(String title, String description, String year) {
        this.title = title;
        this.description = description;
        this.year = year;
    }

    public Movie(Long id, String title, String description, String year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
    }
}
