package com.crud.cinema.backend.facade;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.mapper.EmployeeMapper;
import com.crud.cinema.backend.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeFacade {

    private final DbService dbService;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> getEmployeesList() {
        List<Employee> employees = dbService.getAllEmployees();
        return employees.stream()
                .map(employeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeWithId(Long employeeId) {
        Employee employee = dbService.getEmployeeWithId(employeeId);
        return employeeMapper.mapToEmployeeDto(employee);
    }

    public void createEmployee(final EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        dbService.saveEmployee(employee);
    }

    public EmployeeDto updateEmployee(final EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = dbService.saveEmployee(employee);
        return employeeMapper.mapToEmployeeDto(savedEmployee);
    }

    public void deleteEmployee(Long employeeId) {
        dbService.deleteEmployeeById(employeeId);
    }

}
