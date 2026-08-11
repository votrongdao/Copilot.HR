package com.example.workforce.attendance.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ReviewStatus(UUID correctionId, String status, EmployeeBrief reviewedBy, OffsetDateTime reviewedAt,
        String managerComment) {
}