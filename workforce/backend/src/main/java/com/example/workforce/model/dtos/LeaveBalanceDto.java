package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LeaveBalanceDto(UUID id, UUID employeeId, UUID leaveTypeId, double entitlementAmount, double usedAmount,
        double remainingAmount, LocalDate resetDate, OffsetDateTime updatedAt) {
    public static LeaveBalanceDto sample(UUID id, UUID employeeId) {
        UUID leaveTypeId = UUID.randomUUID();
        return new LeaveBalanceDto(id, employeeId, leaveTypeId, 18, 3.5, 14.5, LocalDate.now().plusMonths(1),
                OffsetDateTime.now());
    }

    public static LeaveBalanceDto sample(UUID id, UUID employeeId, UUID leaveTypeId) {
        return new LeaveBalanceDto(id, employeeId, leaveTypeId, 18, 3.5, 14.5, LocalDate.now().plusMonths(1),
                OffsetDateTime.now());
    }
}
