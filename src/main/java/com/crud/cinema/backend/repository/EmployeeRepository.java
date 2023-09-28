package com.crud.cinema.backend.repository;

import com.crud.cinema.backend.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Override
    List<Employee> findAll();

    @Override
    List<Employee> findAllById(Iterable<Long> longs);
}
