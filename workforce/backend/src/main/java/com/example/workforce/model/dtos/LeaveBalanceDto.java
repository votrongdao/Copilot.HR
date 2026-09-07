package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class LeaveBalanceDto {
    UUID id;
    UUID employeeId;
    LeaveTypeDto leaveType;
    double entitlementAmount;
    double usedAmount;
    double remainingAmount;
    LocalDate resetDate;
    OffsetDateTime updatedAt;
}
