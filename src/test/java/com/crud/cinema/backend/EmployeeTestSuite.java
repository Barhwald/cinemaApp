package com.crud.cinema.backend;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@SpringBootTest
public class EmployeeTestSuite {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveEmployee() {
        //Given
        Employee emp = new Employee("John", "Wick");

        //When
        employeeRepository.save(emp);
        Long empId = emp.getId();

        //Then
        System.out.println(empId);
        assertNotEquals(0, empId);
    }
}
