package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "attendance_record")
public class AttendanceRecordEntity {
    @Id
    private UUID id;

    private UUID employeeId;
    private LocalDate attendanceDate;
    private String status;

    protected AttendanceRecordEntity() {
    }

    public AttendanceRecordEntity(UUID id, UUID employeeId, LocalDate attendanceDate, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }
}