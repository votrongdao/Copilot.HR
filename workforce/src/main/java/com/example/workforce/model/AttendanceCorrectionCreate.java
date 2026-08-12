package com.example.workforce.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceCorrectionCreate(UUID attendanceRecordId, OffsetDateTime requestedClockInAt,
        OffsetDateTime requestedClockOutAt, Integer requestedBreakMinutes, String reasonCategory,
        String employeeJustification) {
}