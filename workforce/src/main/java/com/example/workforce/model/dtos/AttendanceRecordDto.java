package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceRecordDto(UUID id, UUID employeeId, UUID shiftId, LocalDate attendanceDate,
        OffsetDateTime clockInAt, OffsetDateTime clockOutAt, String status) {
    public static AttendanceRecordDto sample(UUID id, UUID employeeId) {
        return new AttendanceRecordDto(id, employeeId, UUID.randomUUID(), LocalDate.now(),
                OffsetDateTime.now().minusHours(8), OffsetDateTime.now(), "present");
    }
}