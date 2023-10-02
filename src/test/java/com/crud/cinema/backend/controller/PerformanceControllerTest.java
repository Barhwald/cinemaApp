package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.facade.PerformanceFacade;
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


import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(PerformanceController.class)
class PerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PerformanceFacade performanceFacade;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .create();
    }

    @Test
    void shouldFetchPerformance() throws Exception {
        //Given
        Movie movie = new Movie("Title", "Desc", "2002");
        Room room = new Room("800");
        PerformanceDto performanceDto = new PerformanceDto(1L,
                "13.09.2023",
                "20:30",
                movie,
                room
                );
        System.out.println(performanceDto);
        when(performanceFacade.getPerformanceWithId(1L)).thenReturn(performanceDto);
        String jsonContent = gson.toJson(performanceDto);


        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/performances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("13.09.2023")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time", Matchers.is("20:30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room.seats", Matchers.is("800")));
    }
}