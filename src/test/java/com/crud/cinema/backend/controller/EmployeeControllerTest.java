package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.facade.EmployeeFacade;
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
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeFacade employeeFacade;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .create();
    }

    @Test
    void shouldFetchEmployee() throws Exception {
        //Given
        EmployeeDto employeeDto = new EmployeeDto(1L, "John", "Feeney");

        when(employeeFacade.getEmployeeWithId(1L)).thenReturn(employeeDto);
        String jsonContent = gson.toJson(employeeDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Feeney")));
    }

    @Test
    void shouldFetchAllEmployees() throws Exception {
        //Given
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Feeney");
        EmployeeDto employeeDto2 = new EmployeeDto(2L, "John", "Deak");
        List<EmployeeDto> employeeDtoList = List.of(employeeDto1, employeeDto2);

        when(employeeFacade.getEmployeesList()).thenReturn(employeeDtoList);
        String jsonContent = gson.toJson(employeeDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Feeney")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Deak")));
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        //Given
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Feeney");

        String jsonContent = gson.toJson(employeeDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        //Given
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Feeney");
        EmployeeDto updatedEmployeeDto1 = new EmployeeDto(1L, "James", "Feeney");

        when(employeeFacade.getEmployeeWithId(1L)).thenReturn(employeeDto1);
        when(employeeFacade.updateEmployee(any(EmployeeDto.class))).thenReturn(updatedEmployeeDto1);

        String jsonContent = gson.toJson(updatedEmployeeDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("James")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Feeney")));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        //Given
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Feeney");

        //When & Then
        doNothing().when(employeeFacade).deleteEmployee(employeeDto1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/employees/{id}", employeeDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}