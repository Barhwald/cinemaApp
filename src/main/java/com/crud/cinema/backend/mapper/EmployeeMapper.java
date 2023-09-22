package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeMapper {

    public Employee mapToEmployee(final EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName()
        );
    }

    public EmployeeDto mapToEmployeeDto(final Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

    public List<EmployeeDto> mapToEmployeeDtoList(final List<Employee> employees) {
        return employees.stream()
                .map(this::mapToEmployeeDto)
                .collect(Collectors.toList());
    }
}
