package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.freecurrency.DataPLN;
import com.crud.cinema.backend.domain.freecurrency.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.freecurrency.client.facade.FreecurrencyFacade;
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
}