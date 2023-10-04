package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.freecurrency.*;
import com.crud.cinema.backend.freecurrency.facade.FreecurrencyFacade;
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
@WebMvcTest(FreecurrencyController.class)
class FreecurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FreecurrencyFacade freecurrencyFacade;

    @Test
    void shouldFetchEurToPlnRate() throws Exception {
        //Given
        DataPLN dataPLN = new DataPLN("4.57");
        FreecurrencyEurToPlnDto freecurrencyEurToPlnDto = new FreecurrencyEurToPlnDto(dataPLN);

        when(freecurrencyFacade.getEutToPlnRate()).thenReturn(freecurrencyEurToPlnDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(freecurrencyEurToPlnDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/freecurrency/eurtopln")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.PLN", Matchers.is("4.57")));
    }

    @Test
    void shouldFetchStatus() throws Exception {
        //Given
        FreecurrencyMonth freecurrencyMonth = new FreecurrencyMonth("2000", "200", "1800");
        FreecurrencyQuotas freecurrencyQuotas = new FreecurrencyQuotas(freecurrencyMonth);
        FreecurrencyStatusDto freecurrencyStatusDto = new FreecurrencyStatusDto(freecurrencyQuotas);

        when(freecurrencyFacade.getQuota()).thenReturn(freecurrencyStatusDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(freecurrencyStatusDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/freecurrency/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is("2000")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.used", Matchers.is("200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remaining", Matchers.is("1800")));
    }
}