package com.example.workforce.model.dtos;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.workforce.common.EmployeeBrief;

import lombok.Data;

@Data
public class AttendanceCorrectionResponse {
    UUID id;
    String status;
    EmployeeBrief employee;
    String reasonCategory;
    String employeeJustification;
    String managerComment;
    OffsetDateTime submittedAt;
    EmployeeBrief reviewedBy;
    OffsetDateTime reviewedAt;
}
