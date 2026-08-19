package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "timesheet")
public class TimesheetEntity {
    @Id
    private UUID id;

    private UUID employeeId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String status;

    protected TimesheetEntity() {
    }

    public TimesheetEntity(UUID id, UUID employeeId, LocalDate weekStart, LocalDate weekEnd, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }
}