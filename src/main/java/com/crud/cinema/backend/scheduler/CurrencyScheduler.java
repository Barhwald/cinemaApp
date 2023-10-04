package com.crud.cinema.backend.scheduler;

import com.crud.cinema.backend.freecurrency.facade.FreecurrencyFacade;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Data
public class CurrencyScheduler {

    private final FreecurrencyFacade freecurrencyFacade;
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyScheduler.class);
    private static CurrencyScheduler instance;

    private CurrencyScheduler(FreecurrencyFacade freecurrencyFacade) {
        this.freecurrencyFacade = freecurrencyFacade;
    }

    public static CurrencyScheduler getInstance(FreecurrencyFacade freecurrencyFacade) {
        if (instance == null) {
            instance = new CurrencyScheduler(freecurrencyFacade);
        }
        return instance;
    }

    @Scheduled(fixedDelay = 20000)
    public void updateFreecurrencyStatus() {
        LOGGER.info(freecurrencyFacade.getQuota().getQuotas().getMonth().toString());
    }
}
