package com.example.workforce.timesheet.model;

import java.util.UUID;

public record TimesheetCorrectionCreate(UUID requestedByEmployeeId, UUID affectedEntryId, String correctionReason,
        String managerComment) {
}