package com.example.workforce.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AttendanceCorrectionCreate {
        UUID attendanceRecordId;
        OffsetDateTime requestedClockInAt;
        OffsetDateTime requestedClockOutAt;
        Integer requestedBreakMinutes;
        String reasonCategory;
        String employeeJustification;
}