package com.example.workforce.repository;

import com.example.workforce.entity.TimesheetEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<TimesheetEntity, UUID> {
}