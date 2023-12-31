package com.crud.cinema.backend.mapper;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.EmployeeDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeMapper {

    @Autowired
    private EmployeeDbService employeeDbService;

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
                employee.getLastName(),
                employeeDbService.getEmployeeWithId(employee.getId()).getRooms().stream().map(Room::getId).collect(Collectors.toSet())
        );
    }

    public List<EmployeeDto> mapToEmployeeDtoList(final List<Employee> employees) {
        return employees.stream()
                .map(this::mapToEmployeeDto)
                .collect(Collectors.toList());
    }
}
