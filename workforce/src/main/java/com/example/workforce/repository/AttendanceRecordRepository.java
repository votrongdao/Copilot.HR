package com.example.workforce.repository;

import com.example.workforce.entity.AttendanceRecordEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecordEntity, UUID> {
}