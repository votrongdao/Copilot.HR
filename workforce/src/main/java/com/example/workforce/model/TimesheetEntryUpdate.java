package com.example.workforce.model;

import java.time.OffsetDateTime;

public record TimesheetEntryUpdate(String projectName, String taskName, OffsetDateTime startAt, OffsetDateTime endAt,
        Integer breakMinutes, Double regularHours, Double overtimeHours) {
}