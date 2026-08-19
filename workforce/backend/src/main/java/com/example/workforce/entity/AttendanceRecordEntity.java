package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import com.example.workforce.enums.AttendanceStatus;

@Entity
@Table(name = "attendance_record")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceRecordEntity {
    @Id
    private UUID id;
    private UUID employeeId;
    private UUID shiftId;
    private UUID departmentId;
    private LocalDate attendanceDate;
    private Timestamp scheduled_start;
    private Timestamp scheduled_end;
    private Timestamp clock_in_at;
    private Timestamp clock_out_at;
    private Integer worked_minutes;
    private AttendanceStatus status;
}