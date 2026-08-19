package com.example.workforce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestCreate(@NotNull UUID employeeId, @NotNull UUID leaveTypeId,
                @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotBlank String reason) {
}
