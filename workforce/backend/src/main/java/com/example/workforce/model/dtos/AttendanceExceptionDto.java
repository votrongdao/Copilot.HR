package com.example.workforce.model.dtos;

import com.example.workforce.common.EmployeeBrief;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceExceptionDto(UUID id, EmployeeBrief employee, LocalDate attendanceDate,
        String exceptionType, String description, String status, OffsetDateTime createdAt) {
    public static AttendanceExceptionDto sample(UUID id, UUID employeeId) {
        return new AttendanceExceptionDto(id, EmployeeBrief.sample(employeeId), LocalDate.now(), "late_arrival",
                "Clock-in is later than the scheduled shift start.", "open", OffsetDateTime.now());
    }
}