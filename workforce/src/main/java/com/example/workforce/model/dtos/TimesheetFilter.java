package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record TimesheetFilter(
        UUID employeeId,
        UUID departmentId, String status,
        LocalDate weekStart, int page,
        int pageSize
) {
    public TimesheetFilter {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = 20;
        }
    }
}
