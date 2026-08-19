package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import com.example.workforce.enums.LeaveRequestStatus;

@Entity
@Table(name = "leave_request")
@Data
@RequiredArgsConstructor
public class LeaveRequestEntity {
    @Id
    private UUID id;
    private UUID employee_id;
    private UUID leave_type_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private int duration_days;
    private int available_balance_at_submit;
    private String reason;
    private LeaveRequestStatus status;
    private LocalDate submitted_at;
    private UUID review_by_employee_id;
    private String manager_comment;
    private LocalDate reviewed_at;
}