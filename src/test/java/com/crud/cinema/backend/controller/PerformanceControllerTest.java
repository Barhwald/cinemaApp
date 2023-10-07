package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.facade.PerformanceFacade;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import net.bytebuddy.NamingStrategy;
import org.apache.tomcat.util.security.Escape;
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


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
        Movie movie = new Movie(1L, "Title", "Desc", "2002");
        Room room = new Room(1L, "800");
        PerformanceDto performanceDto = new PerformanceDto(1L,
                "13.09.2023",
                "20:30",
                movie.getId(),
                room.getId()
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomId", Matchers.is(1)));
    }

    @Test
    void shouldFetchAllPerformances() throws Exception {
        //Given
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "13.10.2023", "13:45", 1L, 1L);
        PerformanceDto performanceDto2 = new PerformanceDto(2L, "14.10.2023", "14:45", 2L, 2L);
        List<PerformanceDto> performanceDtoList = List.of(performanceDto1, performanceDto2);

        when(performanceFacade.getPerformancesList()).thenReturn(performanceDtoList);
        String jsonContent = gson.toJson(performanceDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", Matchers.is("13.10.2023")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", Matchers.is("14.10.2023")));
    }

    @Test
    void shouldCreatePerformance() throws Exception {
        //Given
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "13.10.2023", "13:45", 1L, 2L);

        String jsonContent = gson.toJson(performanceDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdatePerformance() throws Exception {
        //Given
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "13.10.2023", "13:45", 1L, 1L);
        PerformanceDto updatedPerformanceDto1 = new PerformanceDto(1L, "13.10.2024", "20:00", 2L, 2L);

        when(performanceFacade.getPerformanceWithId(1L)).thenReturn(performanceDto1);
        when(performanceFacade.updatePerformance(any(PerformanceDto.class))).thenReturn(updatedPerformanceDto1);

        String jsonContent = gson.toJson(updatedPerformanceDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("13.10.2024")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time", Matchers.is("20:00")));
    }

    @Test
    void shouldDeletePerformance() throws Exception {
        //Given
        PerformanceDto performanceDto1 = new PerformanceDto(1L, "13.10.2023", "13:45", 1L, 1L);

        //When & Then
        doNothing().when(performanceFacade).deletePerformance(performanceDto1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/performances/{id}", performanceDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}