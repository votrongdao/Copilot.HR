package com.example.workforce.model.dtos;

public record AttendanceRecordSummaryDto(long totalRecords, long presentRecords, long lateRecords,
        long absentRecords) {
}