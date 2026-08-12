package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.workforce.model.TimesheetEntryUpdate;

public record TimesheetEntryDto(UUID id, UUID timesheetId, LocalDate workDate, String projectName, String taskName, OffsetDateTime startAt, OffsetDateTime endAt, Integer breakMinutes, double regularHours, double overtimeHours, double totalHours, String entryType, String status) {
    public static TimesheetEntryDto sample(UUID timesheetId, UUID entryId) {
        return new TimesheetEntryDto(entryId, timesheetId, LocalDate.now(), "PRJ-Alpha Redesign", "Component Library Update", OffsetDateTime.now().minusHours(8), OffsetDateTime.now(), 30, 8.0, 0.75, 8.75, "tracked", "draft");
    }

    public TimesheetEntryDto withUpdate(TimesheetEntryUpdate request) {
        return new TimesheetEntryDto(id, timesheetId, workDate, request.projectName() != null ? request.projectName() : projectName, request.taskName() != null ? request.taskName() : taskName, request.startAt() != null ? request.startAt() : startAt, request.endAt() != null ? request.endAt() : endAt, request.breakMinutes() != null ? request.breakMinutes() : breakMinutes, request.regularHours() != null ? request.regularHours() : regularHours, request.overtimeHours() != null ? request.overtimeHours() : overtimeHours, (request.regularHours() != null ? request.regularHours() : regularHours) + (request.overtimeHours() != null ? request.overtimeHours() : overtimeHours), entryType, status);
    }
}