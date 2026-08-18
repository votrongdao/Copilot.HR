package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "leave_request")
public class LeaveRequestEntity {
    @Id
    private UUID id;
    private UUID employeeId;
    private UUID leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    protected LeaveRequestEntity() {
    }

    public LeaveRequestEntity(UUID id, UUID employeeId, UUID leaveTypeId, LocalDate startDate, LocalDate endDate,
            String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }
}