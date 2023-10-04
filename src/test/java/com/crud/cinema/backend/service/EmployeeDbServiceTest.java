package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class EmployeeDbServiceTest {

    @Autowired
    private EmployeeDbService employeeDbService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveEmployee() {
        //Given
        Employee emp = new Employee("John", "Wick");

        //When
        employeeDbService.saveEmployee(emp);
        Long empId = emp.getId();

        //Then
        assertNotEquals(0, empId);
    }

    @Test
    void shouldGetEmployee() {
        //Given
        Employee emp = new Employee("John", "Wayne");

        //When
        employeeDbService.saveEmployee(emp);
        Employee savedEmployee = employeeDbService.getEmployeeWithId(emp.getId());

        //Then
        assertEquals(savedEmployee.getFirstName(), emp.getFirstName());
        assertEquals(savedEmployee.getLastName(), emp.getLastName());
    }

    @Test
    void shouldGetAllEmployees() {
        //Given
        Room room1 = new Room("120");
        Room room2 = new Room("150");
        Room room3 = new Room("160");

        Set<Room> setOne = Set.of(room1, room2);
        Set<Room> setTwo = Set.of(room2, room3);

        Employee emp1 = new Employee("John", "Layne", setOne);
        Employee emp2 = new Employee("John", "Payne", setTwo);
        Employee emp3 = new Employee("John", "Wayne", setOne);

        //When
        employeeDbService.saveEmployee(emp1);
        employeeDbService.saveEmployee(emp2);
        employeeDbService.saveEmployee(emp3);
        List<Employee> employeesList = employeeDbService.getAllEmployees();

        //Then
        assertEquals(3, employeesList.size());
    }

    @Test
    void shouldDeleteEmployee() {
        //Given
        Employee emp = new Employee("John", "Wick");

        //When
        employeeDbService.saveEmployee(emp);
        Long empId = emp.getId();
        employeeDbService.deleteEmployeeById(empId);
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);

        //Then
        assertFalse(optionalEmployee.isPresent());
    }

    @Test
    void getEmployeesWithFirstName() {
        //Given
        Employee employee1 = new Employee(1L, "Mike", "Done");
        Employee employee2 = new Employee(2L, "Hakan", "Doner");
        Employee employee3 = new Employee(3L, "Barbara", "Doie");

        //When
        employeeDbService.saveEmployee(employee1);
        employeeDbService.saveEmployee(employee2);
        employeeDbService.saveEmployee(employee3);
        Set<Employee> employeeSet = employeeDbService.getEmployeesWithFirstName("Mik");

        //Then
        assertEquals(1, employeeSet.size());
        assertEquals("Mike", employeeSet.iterator().next().getFirstName());
        assertEquals("Done", employeeSet.iterator().next().getLastName());
    }

    @Test
    void getEmployeesWithLastName() {
        //Given
        Employee employee1 = new Employee(1L, "Mike", "Done");
        Employee employee2 = new Employee(2L, "Hakan", "Doner");
        Employee employee3 = new Employee(3L, "Barbara", "Doie");

        //When
        employeeDbService.saveEmployee(employee1);
        employeeDbService.saveEmployee(employee2);
        employeeDbService.saveEmployee(employee3);
        Set<Employee> employeeSet = employeeDbService.getEmployeesWithLastName("Doner");

        //Then
        assertEquals(1, employeeSet.size());
        assertEquals("Hakan", employeeSet.iterator().next().getFirstName());
        assertEquals("Doner", employeeSet.iterator().next().getLastName());
    }


}
