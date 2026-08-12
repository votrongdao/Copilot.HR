package com.example.workforce.repository;

import com.example.workforce.entity.DepartmentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID> {
}