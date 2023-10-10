package com.crud.cinema.backend.freecurrency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreecurrencyMonth {

    private String total;
    private String used;
    private String remaining;

}
