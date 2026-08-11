package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AttendanceCorrectionDetail(
        UUID id,
        String status,
        EmployeeBrief employee,
        String reasonCategory,
        String employeeJustification,
        String managerComment,
        OffsetDateTime submittedAt,
        EmployeeBrief reviewedBy,
        OffsetDateTime reviewedAt) {
    public static AttendanceCorrectionDetail sample(UUID id) {
        return new AttendanceCorrectionDetail(id, "pending", EmployeeBrief.sample(UUID.randomUUID()),
                "Forgot to clock in", "Sample justification", null, OffsetDateTime.now(), null, null);
    }

    public static AttendanceCorrectionDetail sample(UUID id, EmployeeBrief employee,
            AttendanceCorrectionCreate request) {
        return new AttendanceCorrectionDetail(id, "pending", employee, request.reasonCategory(),
                request.employeeJustification(), null, OffsetDateTime.now(), null, null);
    }

    public AttendanceCorrectionDetail withStatus(String status) {
        return new AttendanceCorrectionDetail(id, status, employee, reasonCategory, employeeJustification,
                managerComment, submittedAt, reviewedBy, reviewedAt);
    }

    public AttendanceCorrectionDetail withManagerComment(String managerComment) {
        return new AttendanceCorrectionDetail(id, status, employee, reasonCategory, employeeJustification,
                managerComment, submittedAt, reviewedBy, reviewedAt);
    }

    public AttendanceCorrectionDetail withReviewedBy(EmployeeBrief reviewedBy) {
        return new AttendanceCorrectionDetail(id, status, employee, reasonCategory, employeeJustification,
                managerComment, submittedAt, reviewedBy, reviewedAt);
    }

    public AttendanceCorrectionDetail withReviewedAt(OffsetDateTime reviewedAt) {
        return new AttendanceCorrectionDetail(id, status, employee, reasonCategory, employeeJustification,
                managerComment, submittedAt, reviewedBy, reviewedAt);
    }
}