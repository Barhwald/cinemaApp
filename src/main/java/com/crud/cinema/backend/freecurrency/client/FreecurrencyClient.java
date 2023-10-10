package com.crud.cinema.backend.freecurrency.client;

import com.crud.cinema.backend.freecurrency.domain.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.freecurrency.domain.FreecurrencyStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FreecurrencyClient {

    private final RestTemplate restTemplate;
    @Value("${freecurrency.api.endpoint.prod}")
    private String freecurrencyEndpoint;

    @Value("${freecurrency.app.key}")
    private String freecurrencyAppkey;

    // https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_IiHLhudJRRnvqjpNe1XBjfdeYiqmsjlRj7VhLLRl&currencies=PLN&base_currency=EUR

    public FreecurrencyEurToPlnDto getEurToPlnRate() {
        URI url = UriComponentsBuilder.fromHttpUrl(
                freecurrencyEndpoint + "v1/latest?"
        )
                .queryParam("apikey", freecurrencyAppkey)
                .queryParam("currencies", "PLN")
                .queryParam("base_currency", "EUR")
                .build()
                .encode()
                .toUri();
        FreecurrencyEurToPlnDto freecurrencyResponse = restTemplate.getForObject(url, FreecurrencyEurToPlnDto.class);

        return Optional.ofNullable(freecurrencyResponse).orElse(new FreecurrencyEurToPlnDto());
    }

    public FreecurrencyStatusDto getStatus() {
        URI url = UriComponentsBuilder.fromHttpUrl(
                        freecurrencyEndpoint + "v1/status"
                )
                .queryParam("apikey", freecurrencyAppkey)
                .build()
                .encode()
                .toUri();
        FreecurrencyStatusDto freecurrencyResponse = restTemplate.getForObject(url, FreecurrencyStatusDto.class);

        return Optional.ofNullable(freecurrencyResponse).orElse(new FreecurrencyStatusDto());
    }

}
