package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record AttendanceCorrectionFilter(
        String status,
        UUID employeeId,
        LocalDate dateFrom,
        LocalDate dateTo,
        int page,
        int pageSize) {
    public AttendanceCorrectionFilter {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = 20;
        }
        if (dateFrom != null && dateTo != null && dateTo.isBefore(dateFrom)) {
            throw new IllegalArgumentException("date_to must be on or after date_from");
        }
    }
}
