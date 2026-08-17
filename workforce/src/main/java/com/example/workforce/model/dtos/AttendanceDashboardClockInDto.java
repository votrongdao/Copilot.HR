package com.example.workforce.model.dtos;

import com.example.workforce.common.EmployeeBrief;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceDashboardClockInDto(UUID id, EmployeeBrief employee, OffsetDateTime clockInAt,
        UUID shiftId, String status) {
}