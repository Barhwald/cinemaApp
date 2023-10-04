package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.mapper.EmployeeMapper;
import com.crud.cinema.backend.service.EmployeeDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
class EmployeeFacadeTest {
    @InjectMocks
    private EmployeeFacade employeeFacade;
    @Mock
    private EmployeeDbService employeeDbService;
    @Mock
    private EmployeeMapper employeeMapper;

    @Test
    void shouldGetEmployeesList() {
        //Given
        Employee employee1 = new Employee("Mike", "Dany");
        Employee employee2 = new Employee("Mikey", "Danylis");
        List<Employee> employeeList = List.of(employee1, employee2);

        when(employeeDbService.getAllEmployees()).thenReturn(employeeList);

        //When
        List<EmployeeDto> employeeDtoList = employeeFacade.getEmployeesList();

        //Then
        assertEquals(2, employeeDtoList.size());
    }

    @Test
    void shouldGetEmployeeWithId() {
        //Given
        Employee employee1 = new Employee(1L, "Mike", "Dany");
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "Mike", "Dany");

        when(employeeDbService.getEmployeeWithId(1L)).thenReturn(employee1);
        when(employeeMapper.mapToEmployeeDto(employee1)).thenReturn(employeeDto1);

        //When
        EmployeeDto resultEmployeeDto = employeeFacade.getEmployeeWithId(1L);

        //Then
        assertEquals("Mike", resultEmployeeDto.getFirstName());
        assertEquals("Dany", resultEmployeeDto.getLastName());
    }

    @Test
    void shouldCreateEmployee() {
        //Given
        Employee employee1 = new Employee(1L, "Mike", "Dany");
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "Mike", "Dany");

        when(employeeMapper.mapToEmployee(employeeDto1)).thenReturn(employee1);

        //When
        employeeFacade.createEmployee(employeeDto1);

        //Then
        verify(employeeMapper).mapToEmployee(employeeDto1);
        verify(employeeDbService).saveEmployee(employee1);
    }

    @Test
    void shouldUpdateEmployee() {
        //Given
        EmployeeDto employeeDto = new EmployeeDto("Mike", "Dean");
        Employee mappedEmployee = new Employee("Mike", "Dean");
        Employee savedEmployee = new Employee(1L, "Mike", "Dean");

        when(employeeMapper.mapToEmployee(employeeDto)).thenReturn(mappedEmployee);
        when(employeeDbService.saveEmployee(mappedEmployee)).thenReturn(savedEmployee);
        when(employeeMapper.mapToEmployeeDto(savedEmployee)).thenReturn(employeeDto);

        //When
        EmployeeDto updatedEmployeeDto = employeeFacade.updateEmployee(employeeDto);

        //Then
        verify(employeeMapper).mapToEmployee(employeeDto);
        verify(employeeDbService).saveEmployee(mappedEmployee);
        verify(employeeMapper).mapToEmployeeDto(savedEmployee);
        assertEquals(employeeDto, updatedEmployeeDto);
    }

    @Test
    void shouldDeleteEmployee() {
        //Given
        Long employeeId = 1L;

        //When
        employeeFacade.deleteEmployee(employeeId);

        //Then
        verify(employeeDbService).deleteEmployeeById(employeeId);
    }
}