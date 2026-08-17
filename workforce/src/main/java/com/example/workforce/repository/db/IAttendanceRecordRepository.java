package com.example.workforce.repository.db;
import com.example.workforce.entity.AttendanceRecordEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAttendanceRecordRepository extends JpaRepository<AttendanceRecordEntity, UUID> {
}