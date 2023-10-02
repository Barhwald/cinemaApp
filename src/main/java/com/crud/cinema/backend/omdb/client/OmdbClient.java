package com.crud.cinema.backend.omdb.client;

import com.crud.cinema.backend.domain.OmdbMovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OmdbClient {

    private final RestTemplate restTemplate;
    @Value("${omdb.api.endpoint.prod}")
    private String omdbApiEndpoint;
    @Value("${omdb.app.key}")
    private String omdbAppKey;

//    https://www.omdbapi.com/?apikey=1ce793d5&t=park

    public OmdbMovieDto getOmdbMovieByTitle(String movieTitle) {
        URI url = UriComponentsBuilder.fromHttpUrl(
                omdbApiEndpoint + "/?"
        )
                .queryParam("apikey", omdbAppKey)
                .queryParam("t", movieTitle)
                .build()
                .encode()
                .toUri();
        OmdbMovieDto omdbResponse = restTemplate.getForObject(url, OmdbMovieDto.class);

        return Optional.ofNullable(omdbResponse)
                .orElse(new OmdbMovieDto());
    }
}
