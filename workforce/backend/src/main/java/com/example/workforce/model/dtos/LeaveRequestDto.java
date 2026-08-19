package com.example.workforce.model.dtos;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.model.LeaveRequestCreate;
import com.example.workforce.model.LeaveRequestUpdate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LeaveRequestDto(
        UUID id,
        EmployeeBrief employee,
        LeaveTypeDto leaveType,
        LocalDate startDate,
        LocalDate endDate,
        double durationDays,
        String reason,
        String status,
        OffsetDateTime submittedAt,
        EmployeeBrief reviewedBy,
        String managerComment,
        OffsetDateTime reviewedAt) {
    public static LeaveRequestDto sample(UUID id) {
        return new LeaveRequestDto(
                id,
                EmployeeBrief.sample(UUID.randomUUID()),
                new LeaveTypeDto(UUID.randomUUID(), "Annual Leave", "active"),
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                3,
                "Vacation",
                "pending",
                OffsetDateTime.now(),
                null,
                null,
                null);
    }

    public static LeaveRequestDto sample(UUID id, EmployeeBrief employee, LeaveRequestCreate request) {
        return new LeaveRequestDto(
                id,
                employee,
                new LeaveTypeDto(request.leaveTypeId() != null ? request.leaveTypeId() : UUID.randomUUID(),
                        "Annual Leave", "active"),
                request.startDate(),
                request.endDate(),
                Math.max(1, request.endDate().toEpochDay() - request.startDate().toEpochDay() + 1),
                request.reason(),
                "pending",
                OffsetDateTime.now(),
                null,
                null,
                null);
    }

    public LeaveRequestDto withUpdate(LeaveRequestUpdate request) {
        return new LeaveRequestDto(
                id,
                employee,
                leaveType,
                request.startDate() != null ? request.startDate() : startDate,
                request.endDate() != null ? request.endDate() : endDate,
                Math.max(1,
                        (request.endDate() != null ? request.endDate() : endDate).toEpochDay()
                                - (request.startDate() != null ? request.startDate() : startDate).toEpochDay() + 1),
                request.reason() != null ? request.reason() : reason,
                status,
                submittedAt,
                reviewedBy,
                managerComment,
                reviewedAt);
    }

    public LeaveRequestDto withStatus(String newStatus) {
        return new LeaveRequestDto(id, employee, leaveType, startDate, endDate, durationDays, reason, newStatus,
                submittedAt, reviewedBy, managerComment, reviewedAt);
    }
}
