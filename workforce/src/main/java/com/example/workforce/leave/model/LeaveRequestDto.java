package com.example.workforce.leave.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LeaveRequestDto(UUID id, EmployeeBrief employee, LeaveTypeDto leaveType, LocalDate startDate,
        LocalDate endDate, int durationDays, String reason, String status, OffsetDateTime submittedAt,
        EmployeeBrief reviewedBy, String managerComment, OffsetDateTime reviewedAt) {
    public static LeaveRequestDto sample(UUID id) {
        UUID employeeId = UUID.randomUUID();
        return new LeaveRequestDto(id, EmployeeBrief.sample(employeeId),
                new LeaveTypeDto(UUID.randomUUID(), "Annual Leave", "active"), LocalDate.now(),
                LocalDate.now().plusDays(2), 3, "Vacation", "pending", OffsetDateTime.now(), null, null, null);
    }

    public static LeaveRequestDto sample(UUID id, EmployeeBrief employee, LeaveRequestCreate request) {
        return new LeaveRequestDto(id, employee, new LeaveTypeDto(request.leaveTypeId(), "Annual Leave", "active"),
                request.startDate(), request.endDate(),
                Math.max(1, request.endDate().getDayOfYear() - request.startDate().getDayOfYear() + 1),
                request.reason(), "pending", OffsetDateTime.now(), null, null, null);
    }

    public LeaveRequestDto withUpdate(LeaveRequestUpdate request) {
        LocalDate newStart = request.startDate() != null ? request.startDate() : startDate;
        LocalDate newEnd = request.endDate() != null ? request.endDate() : endDate;
        return new LeaveRequestDto(id, employee, leaveType, newStart, newEnd,
                Math.max(1, newEnd.getDayOfYear() - newStart.getDayOfYear() + 1),
                request.reason() != null ? request.reason() : reason, status, submittedAt, reviewedBy, managerComment,
                reviewedAt);
    }

    public LeaveRequestDto withStatus(String newStatus) {
        return new LeaveRequestDto(id, employee, leaveType, startDate, endDate, durationDays, reason, newStatus,
                submittedAt, reviewedBy, managerComment, reviewedAt);
    }
}