package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String year;


    public MovieDto(String title, String description, String year) {
        this.title = title;
        this.description = description;
        this.year = year;
    }
}
