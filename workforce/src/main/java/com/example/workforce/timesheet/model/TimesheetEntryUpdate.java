package com.example.workforce.timesheet.model;

import java.time.OffsetDateTime;

public record TimesheetEntryUpdate(String projectName, String taskName, OffsetDateTime startAt, OffsetDateTime endAt,
        Integer breakMinutes, Double regularHours, Double overtimeHours) {
}