package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.service.EmployeeDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeMapperTest {

    @InjectMocks
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeDbService employeeDbService;

    @Test
    void shouldMapToEmployee() {
        //Given
        EmployeeDto employeeDto = new EmployeeDto(1L, "Harry", "Maine");

        //When
        Employee employee = employeeMapper.mapToEmployee(employeeDto);

        //Then
        assertEquals(1, employee.getId());
        assertEquals("Harry", employee.getFirstName());
        assertEquals("Maine", employee.getLastName());
    }

    @Test
    void shouldMapToEmployeeDto() {
        //Given
        Employee employee = new Employee(1L, "Harry", "Jones");
        when(employeeDbService.getEmployeeWithId(1L)).thenReturn(employee);

        //When
        EmployeeDto employeeDto = employeeMapper.mapToEmployeeDto(employee);

        //Then
        assertEquals(1, employeeDto.getId());
        assertEquals("Harry", employeeDto.getFirstName());
        assertEquals("Jones", employeeDto.getLastName());
    }

    @Test
    void shouldMapToEmployeeDtoList() {
        //Given
        Employee employee1 = new Employee(1L, "Harry", "Jones");
        Employee employee2 = new Employee(2L, "Larry", "Jones");
        Employee employee3 = new Employee(3L, "Marry", "Jones");
        List<Employee> employees = List.of(employee1, employee2, employee3);

        when(employeeDbService.getEmployeeWithId(1L)).thenReturn(employee1);
        when(employeeDbService.getEmployeeWithId(2L)).thenReturn(employee2);
        when(employeeDbService.getEmployeeWithId(3L)).thenReturn(employee3);

        //When
        List<EmployeeDto> employeeDtoList = employeeMapper.mapToEmployeeDtoList(employees);

        //Then
        assertEquals(3, employeeDtoList.size());
    }

}