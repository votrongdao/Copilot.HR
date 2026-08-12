package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceCorrectionListItem(UUID id, EmployeeBrief employee, LocalDate attendanceDate,
        String reasonCategory, String status, OffsetDateTime submittedAt) {
}