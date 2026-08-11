package com.example.workforce.leave.model;

import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestCreate(UUID employeeId, UUID leaveTypeId, LocalDate startDate, LocalDate endDate,
        String reason) {
}