package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.freecurrency.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.domain.freecurrency.FreecurrencyStatusDto;
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
