package com.example.workforce.timesheet.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TimesheetDetailDto(UUID id, EmployeeBrief employee, LocalDate weekStart, LocalDate weekEnd, String status, double regularHours, double overtimeHours, double totalHours, Double billableHours, Double billableAmount, OffsetDateTime submittedAt, EmployeeBrief reviewedBy, OffsetDateTime reviewedAt) {
    public static TimesheetDetailDto sample(UUID id) {
        return new TimesheetDetailDto(id, EmployeeBrief.sample(UUID.randomUUID()), LocalDate.now().withDayOfMonth(1), LocalDate.now().withDayOfMonth(7), "submitted", 38.5, 4.0, 42.5, 40.0, 2840.00, OffsetDateTime.now(), null, null);
    }

    public TimesheetDetailDto withStatus(String status) {
        return new TimesheetDetailDto(id, employee, weekStart, weekEnd, status, regularHours, overtimeHours, totalHours, billableHours, billableAmount, submittedAt, reviewedBy, reviewedAt);
    }

    public TimesheetDetailDto withReviewedBy(EmployeeBrief reviewedBy) {
        return new TimesheetDetailDto(id, employee, weekStart, weekEnd, status, regularHours, overtimeHours, totalHours, billableHours, billableAmount, submittedAt, reviewedBy, reviewedAt);
    }

    public TimesheetDetailDto withReviewedAt(OffsetDateTime reviewedAt) {
        return new TimesheetDetailDto(id, employee, weekStart, weekEnd, status, regularHours, overtimeHours, totalHours, billableHours, billableAmount, submittedAt, reviewedBy, reviewedAt);
    }
}