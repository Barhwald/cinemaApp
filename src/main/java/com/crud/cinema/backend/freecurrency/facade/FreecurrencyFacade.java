package com.crud.cinema.backend.freecurrency.facade;

import com.crud.cinema.backend.freecurrency.domain.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.freecurrency.domain.FreecurrencyStatusDto;
import com.crud.cinema.backend.freecurrency.service.FreecurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FreecurrencyFacade {

    private final FreecurrencyService freecurrencyService;

    public FreecurrencyEurToPlnDto getEutToPlnRate() {
        return freecurrencyService.getEurToPlnRate();
    }
    public FreecurrencyStatusDto getQuota() {
        return freecurrencyService.getStatus();
    }
}
