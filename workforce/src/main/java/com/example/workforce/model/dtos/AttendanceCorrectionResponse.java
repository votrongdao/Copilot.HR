package com.example.workforce.model.dtos;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

public class AttendanceCorrectionResponse {
    private UUID id;

    private UUID attendanceRecordId;
    private UUID employeeId;

    private Timestamp originalClockInAt;
    private Timestamp requestedClockInAt;

    private Timestamp originalClockOutAt;
    private Timestamp requestedClockOutAt;

    private Integer originalBreakMinutes;
    private Integer requestedBreakMinutes;

    private String reasonCategory;
    private String employeeJustification;
    private String status;

    private Date submittedAt;

    private UUID reviewedByEmployeeId;

    private String managerComment;

    private Timestamp reviewedAt;
}
