package com.crud.cinema.backend.omdb.facade;

import com.crud.cinema.backend.domain.OmdbMovieDto;
import com.crud.cinema.backend.service.OmdbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class OmdbFacadeTest {
    @InjectMocks
    private OmdbFacade omdbFacade;
    @Mock
    private OmdbService omdbService;

    @Test
    void getOmdbMovieByTitle() {
        //Given
        String movieTitle = "Example Movie";
        OmdbMovieDto expectedMovieDto = new OmdbMovieDto();
        expectedMovieDto.setTitle(movieTitle);
        when(omdbService.getOmdbMovieByTitle(movieTitle)).thenReturn(expectedMovieDto);

        //When
        OmdbMovieDto result = omdbFacade.getOmdbMovieByTitle(movieTitle);

        //Then
        assertEquals(expectedMovieDto, result);
    }
}