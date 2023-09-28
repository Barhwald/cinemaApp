package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.OmdbMovieDto;
import com.crud.cinema.backend.omdb.facade.OmdbFacade;
import com.nimbusds.jose.shaded.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(OmdbController.class)
public class OmdbControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OmdbFacade omdbFacade;

    @Test
    void shouldFetchOmdbMovie() throws Exception {
        //Given
        OmdbMovieDto omdbMovieDto = new OmdbMovieDto("Some title", "Some plot", "1969", "4.5");

        when(omdbFacade.getOmdbMovieByTitle("title")).thenReturn(omdbMovieDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(omdbMovieDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/omdb/movies/title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Title", Matchers.is("Some title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Plot", Matchers.is("Some plot")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Year", Matchers.is("1969")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imdbRating", Matchers.is("4.5")));
    }

}
