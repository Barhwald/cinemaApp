package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.adapter.LocalDateTimeTypeAdapter;
import com.crud.cinema.backend.domain.RoomDto;
import com.crud.cinema.backend.facade.RoomFacade;
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
@WebMvcTest(RoomController.class)
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomFacade roomFacade;
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
    void shouldFetchRoom() throws Exception {
        //Given
        RoomDto roomDto = new RoomDto(1L, "78");

        when(roomFacade.getRoomWithId(1L)).thenReturn(roomDto);
        String jsonContent = gson.toJson(roomDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seats", Matchers.is(78)));
    }

    @Test
    void shouldFetchAllRooms() throws Exception {
        //Given
        RoomDto roomDto1 = new RoomDto(1L, "78");
        RoomDto roomDto2 = new RoomDto(2L, "156");
        List<RoomDto> roomDtoList = List.of(roomDto1, roomDto2);

        when(roomFacade.getRoomsList()).thenReturn(roomDtoList);
        String jsonContent = gson.toJson(roomDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seats", Matchers.is(78)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seats", Matchers.is(156)));
    }

    @Test
    void shouldCreateRoom() throws Exception {
        //Given
        RoomDto roomDto1 = new RoomDto(1L, "78");

        String jsonContent = gson.toJson(roomDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateRoom() throws Exception {
        //Given
        RoomDto roomDto1 = new RoomDto(1L, "78");
        RoomDto updatedRoomDto1 = new RoomDto(1L, "80");

        when(roomFacade.getRoomWithId(1L)).thenReturn(roomDto1);
        when(roomFacade.updateRoom(any(RoomDto.class))).thenReturn(updatedRoomDto1);

        String jsonContent = gson.toJson(updatedRoomDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seats", Matchers.is(80)));
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        //Given
        RoomDto roomDto1 = new RoomDto(1L, "78");

        //When & Then
        doNothing().when(roomFacade).deleteRoom(roomDto1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/rooms/{id}", roomDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}