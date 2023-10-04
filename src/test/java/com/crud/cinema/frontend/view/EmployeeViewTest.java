package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.service.EmployeeDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class EmployeeViewTest {

    @InjectMocks
    private EmployeeView employeeView;

    @Mock
    private EmployeeDbService employeeDbService;

    @Test
    void shouldSetColumnsProperly() {
        //Given

        //When & Then
        assertEquals("id", employeeView.getEmployeeGrid().getColumns().get(0).getKey());
        assertEquals("firstName", employeeView.getEmployeeGrid().getColumns().get(1).getKey());
        assertEquals("lastName", employeeView.getEmployeeGrid().getColumns().get(2).getKey());
    }

    @Test
    void shouldFilterEmployeeById() {
        Employee employee = new Employee(1L, "Mike", "O'Hara");
        when(employeeDbService.getEmployeesWithId("1")).thenReturn(Set.of(employee));

        //When
        employeeView.getFilter1().setValue("1");

        //Then
        assertEquals(1, employeeDbService.getEmployeesWithId(employeeView.getFilter1().getValue()).size());
    }

    @Test
    void shouldFilterEmployeeByFirstName() {
        Employee employee = new Employee(1L, "Mike", "O'Hara");
        when(employeeDbService.getEmployeesWithFirstName("Mike")).thenReturn(Set.of(employee));

        //When
        employeeView.getFilter2().setValue("Mike");

        //Then
        assertEquals(1, employeeDbService.getEmployeesWithFirstName(employeeView.getFilter2().getValue()).size());
    }

    @Test
    void shouldFilterEmployeeByLastName() {
        Employee employee = new Employee(1L, "Mike", "O'Hara");
        when(employeeDbService.getEmployeesWithLastName("O'Hara")).thenReturn(Set.of(employee));

        //When
        employeeView.getFilter3().setValue("O'Hara");

        //Then
        assertEquals(1, employeeDbService.getEmployeesWithLastName(employeeView.getFilter3().getValue()).size());
    }
}