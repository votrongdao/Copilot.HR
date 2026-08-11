package com.example.workforce.leave.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LeaveBalanceAdjustmentDto(UUID id, UUID leaveBalanceId, double previousAmount, double adjustmentAmount, double newAmount, String reason, EmployeeBrief adjustedBy, OffsetDateTime adjustedAt) {
}