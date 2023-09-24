package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.adapter.LocalDateTimeTypeAdapter;
import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.facade.MovieFacade;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieFacade movieFacade;
    @MockBean
    private LocalDateTimeTypeAdapter localDateTimeTypeAdapter;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, localDateTimeTypeAdapter)
                .create();
    }

    @Test
    void shouldFetchMovie() throws Exception {
        //Given
        MovieDto movieDto = new MovieDto(1L, "Title", "Descblablabla", "2002");

        when(movieFacade.getMovieWithId(1L)).thenReturn(movieDto);
        String jsonContent = gson.toJson(movieDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Descblablabla")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is("2002")));
    }

    @Test
    void shouldFetchAllMovies() throws Exception {
        //Given
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Descblablabla", "2002");
        MovieDto movieDto2 = new MovieDto(2L, "Title2", "Descblablabla2", "20022");
        List<MovieDto> movieDtoList = List.of(movieDto1, movieDto2);

        when(movieFacade.getMoviesList()).thenReturn(movieDtoList);
        String jsonContent = gson.toJson(movieDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Title2")));
    }

    @Test
    void shouldCreateMovie() throws Exception {
        //Given
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Descblablabla", "2002");

        String jsonContent = gson.toJson(movieDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        //Given
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Descblablabla", "2002");
        MovieDto updatedMovieDto1 = new MovieDto(1L, "Titlezzzz", "Descblablabla", "2002");

        when(movieFacade.getMovieWithId(1L)).thenReturn(movieDto1);
        when(movieFacade.updateMovie(any(MovieDto.class))).thenReturn(updatedMovieDto1);

        String jsonContent = gson.toJson(updatedMovieDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Titlezzzz")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Descblablabla")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is("2002")));
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        //Given
        MovieDto movieDto1 = new MovieDto(1L, "Title", "Descblablabla", "2002");

        //When & Then
        doNothing().when(movieFacade).deleteMovie(movieDto1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/movies/{id}", movieDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}