package com.example.workforce.model.dtos;

import java.time.LocalDate;

public record AttendanceCorrectionExportFilter(
        String status,
        LocalDate dateFrom,
        LocalDate dateTo) {

}
