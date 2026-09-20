package com.example.workforce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class LeaveRequestCreate {
    @NotNull
    private UUID employeeId;
    @NotNull
    private UUID leaveTypeId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    private String reason;
}