package com.example.workforce.model.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.workforce.model.TimesheetCorrectionCreate;

public record TimesheetCorrectionDto(UUID id, UUID timesheetId, UUID affectedEntryId, UUID requestedByEmployeeId,
        String correctionReason, String managerComment, String status, OffsetDateTime requestedAt,
        OffsetDateTime resolvedAt) {
    public static TimesheetCorrectionDto sample(UUID timesheetId, TimesheetCorrectionCreate request) {
        return new TimesheetCorrectionDto(UUID.randomUUID(), timesheetId, request.affectedEntryId(),
                request.requestedByEmployeeId() != null ? request.requestedByEmployeeId() : UUID.randomUUID(),
                request.correctionReason(), request.managerComment(), "pending", OffsetDateTime.now(), null);
    }
}