package com.crud.cinema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDto {

    private Long id;
    private String date;
    private String time;
    private Movie movie;
    private Room room;

}
