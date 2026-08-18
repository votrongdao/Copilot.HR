package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceCorrectionListItem {
        private UUID id;
        private EmployeeBrief employee;
        private LocalDate attendanceDate;
        private String reasonCategory;
        private String status;
        private OffsetDateTime submittedAt;
}