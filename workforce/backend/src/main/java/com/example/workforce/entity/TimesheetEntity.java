package com.example.workforce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "timesheet")
@Data
@NoArgsConstructor
public class TimesheetEntity {
    @Id
    private UUID id;
    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "week_start")
    private LocalDate weekStart;
    @Column(name = "week_end")
    private LocalDate weekEnd;
    @Column(name = "status")
    private String status;
}