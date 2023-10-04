package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDbService {

    private final EmployeeRepository employeeRepository;

    public Employee saveEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeWithId(long id) {
        return employeeRepository.findById(id).isPresent() ? employeeRepository.findById(id).get() : null;
    }
    public List<Employee> getEmployeesWithId(List<Long> ids) {
        return employeeRepository.findAllById(ids);
    }
    public Set<Employee> getEmployeesWithId(String id) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getId().toString().contains(id))
                .collect(Collectors.toSet());
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public boolean deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public Set<Employee> getEmployeesWithFirstName(String value) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getFirstName().toLowerCase().contains(value.toLowerCase()))
                .collect(Collectors.toSet());
    }

    public Set<Employee> getEmployeesWithLastName(String value) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getLastName().toLowerCase().contains(value.toLowerCase()))
                .collect(Collectors.toSet());
    }
}
