package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.EmployeeRepository;
import com.crud.cinema.backend.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class EmployeeTestSuite {

    @Autowired
    private DbService dbService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveEmployee() {
        //Given
        Employee emp = new Employee("John", "Wick");

        //When
        dbService.saveEmployee(emp);
        Long empId = emp.getId();

        //Then
        assertNotEquals(0, empId);
    }

    @Test
    void shouldGetEmployee() {
        //Given
        Employee emp = new Employee("John", "Wayne");

        //When
        dbService.saveEmployee(emp);
        Employee savedEmployee = dbService.getEmployeeWithId(emp.getId());

        //Then
        assertEquals(savedEmployee.getFirstName(), emp.getFirstName());
        assertEquals(savedEmployee.getLastName(), emp.getLastName());
    }

    @Test
    void shouldGetAllEmployees() {
        //Given
        Room room1 = new Room(120L);
        Room room2 = new Room(150L);
        Room room3 = new Room(160L);

        List<Room> listOne = List.of(room1, room2);
        List<Room> listTwo = List.of(room2, room3);

        Employee emp1 = new Employee("John", "Layne", listOne);
        Employee emp2 = new Employee("John", "Payne", listTwo);
        Employee emp3 = new Employee("John", "Wayne", listOne);

        //When
        dbService.saveEmployee(emp1);
        dbService.saveEmployee(emp2);
        dbService.saveEmployee(emp3);
        List<Employee> employeesList = dbService.getAllEmployees();

        //Then
        assertEquals(3, employeesList.size());
    }

    @Test
    void shouldDeleteEmployee() {
        //Given
        Employee emp = new Employee("John", "Wick");

        //When
        dbService.saveEmployee(emp);
        Long empId = emp.getId();
        dbService.deleteEmployeeById(empId);
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);

        //Then
        assertFalse(optionalEmployee.isPresent());
    }
}
