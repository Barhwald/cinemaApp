package com.crud.cinema.backend.freecurrency.service;

import com.crud.cinema.backend.freecurrency.domain.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.freecurrency.domain.FreecurrencyStatusDto;
import com.crud.cinema.backend.freecurrency.client.FreecurrencyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreecurrencyService {

    private final FreecurrencyClient freecurrencyClient;

    public FreecurrencyEurToPlnDto getEurToPlnRate() {
        return freecurrencyClient.getEurToPlnRate();
    }

    public FreecurrencyStatusDto getStatus() {
        return freecurrencyClient.getStatus();
    }
}
