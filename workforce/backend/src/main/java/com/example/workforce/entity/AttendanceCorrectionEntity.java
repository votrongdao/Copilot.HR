package com.example.workforce.entity;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.workforce.enums.AttendanceCorrectionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCorrectionEntity {
    @Id
    private UUID id;
    @Column(name = "organization_id")
    private UUID organizationId;
    @Column(name = "attendance_record_id")
    private UUID attendanceRecordId;
    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "original_clock_in_at")
    private Timestamp originalClockInAt;
    @Column(name = "requested_clock_in_at")
    private Timestamp requestedClockInAt;

    @Column(name = "original_clock_out_at")
    private Timestamp originalClockOutAt;
    @Column(name = "requested_clock_out_at")
    private Timestamp requestedClockOutAt;

    @Column(name = "original_break_minutes")
    private Integer originalBreakMinutes;
    @Column(name = "requested_break_minutes")
    private Integer requestedBreakMinutes;

    @Column(name = "reason_category")
    private String reasonCategory;
    @Column(name = "employee_justification")
    private String employeeJustification;
    @Column(name = "status")
    private AttendanceCorrectionStatus status;

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt;

    @Column(name = "reviewed_by_employee_id")
    private UUID reviewedByEmployeeId;

    @Column(name = "manager_comment")
    private String managerComment;

    @Column(name = "reviewed_at")
    private OffsetDateTime reviewedAt;
}