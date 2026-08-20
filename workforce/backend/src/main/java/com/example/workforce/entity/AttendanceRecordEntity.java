package com.example.workforce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import javax.annotation.processing.Generated;

import com.example.workforce.enums.AttendanceStatus;

@Entity
@Table(name = "attendance_record")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "shift_id")
    private UUID shiftId;
    @Column(name = "department_id")
    private UUID departmentId;
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;
    @Column(name = "scheduled_start")
    private Timestamp scheduledStart;
    @Column(name = "scheduled_end")
    private Timestamp scheduledEnd;
    @Column(name = "clock_in_at")
    private Timestamp clockInAt;
    @Column(name = "clock_out_at")
    private Timestamp clockOutAt;
    @Column(name = "worked_minutes")
    private Integer workedMinutes;
    private AttendanceStatus status;
}