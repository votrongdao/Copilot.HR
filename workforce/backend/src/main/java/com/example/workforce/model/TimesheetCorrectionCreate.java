package com.example.workforce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record TimesheetCorrectionCreate(@NotNull UUID requestedByEmployeeId, UUID affectedEntryId,
        @NotBlank String correctionReason,
        String managerComment) {
}
