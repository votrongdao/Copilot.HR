package com.example.workforce.timesheet.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.LocalDate;
import java.util.UUID;

public record TimesheetListItemDto(UUID id, EmployeeBrief employee, LocalDate weekStart, LocalDate weekEnd,
        double totalHours, String status) {
}