package com.crud.cinema.backend.freecurrency.client.facade;

import com.crud.cinema.backend.domain.freecurrency.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.service.FreecurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FreecurrencyFacade {

    private final FreecurrencyService freecurrencyService;

    public FreecurrencyEurToPlnDto getEutToPlnRate() {
        return freecurrencyService.getEurToPlnRate();
    }
}
