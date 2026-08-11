package com.example.workforce.attendance.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceBreakDto(UUID id, UUID attendanceRecordId, OffsetDateTime breakStartAt,
        OffsetDateTime breakEndAt, Integer durationMinutes) {
    public static AttendanceBreakDto sample(UUID id, UUID attendanceRecordId) {
        return new AttendanceBreakDto(id, attendanceRecordId, OffsetDateTime.now().minusMinutes(30),
                OffsetDateTime.now().minusMinutes(10), 20);
    }
}