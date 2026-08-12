package com.example.workforce.repository;

import com.example.workforce.entity.EmployeeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
}