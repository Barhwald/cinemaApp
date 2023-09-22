package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.facade.EmployeeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeFacade employeeFacade;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return ResponseEntity.ok(employeeFacade.getEmployeesList());
    }

    @GetMapping(value = "{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeWithId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeFacade.getEmployeeWithId(employeeId));
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeFacade.createEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeFacade.updateEmployee(employeeDto));
    }

//    @PutMapping
//    public ResponseEntity<EmployeeDto> updateRoomsForEmployee(@RequestBody EmployeeDto employeeDto) {
//        Employee employee = dbService.getEmployeeWithId(employeeDto.getId());
//        employee.setRooms(employeeDto.getRooms());
//        Employee savedEmployee = dbService.saveEmployee(employee);
//        return ResponseEntity.ok(employeeMapper.mapToEmployeeDto(savedEmployee));
//    }

    @DeleteMapping(value = "{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeFacade.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}
