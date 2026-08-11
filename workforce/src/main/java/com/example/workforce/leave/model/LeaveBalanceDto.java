package com.example.workforce.leave.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LeaveBalanceDto(UUID id, UUID employeeId, LeaveTypeDto leaveType, double entitlementAmount,
        double usedAmount, double remainingAmount, LocalDate resetDate, OffsetDateTime updatedAt) {
    public static LeaveBalanceDto sample(UUID id, UUID employeeId) {
        return new LeaveBalanceDto(id, employeeId, new LeaveTypeDto(UUID.randomUUID(), "Annual Leave", "active"), 18,
                3.5, 14.5, LocalDate.now().plusMonths(1), OffsetDateTime.now());
    }

    public static LeaveBalanceDto sample(UUID id, UUID employeeId, UUID leaveTypeId) {
        return new LeaveBalanceDto(id, employeeId, new LeaveTypeDto(leaveTypeId, "Annual Leave", "active"), 18, 3.5,
                14.5, LocalDate.now().plusMonths(1), OffsetDateTime.now());
    }
}