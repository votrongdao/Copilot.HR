package com.example.workforce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.workforce.entity.EmployeeEntity;

public interface IEmployeeRepository {
    EmployeeEntity save(EmployeeEntity employeeEntity);

    Optional<EmployeeEntity> findById(UUID id);

    List<EmployeeEntity> findAll();
}
