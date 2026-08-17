package com.example.workforce.entity;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;
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