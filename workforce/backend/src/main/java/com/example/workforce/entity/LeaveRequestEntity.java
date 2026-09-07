package com.example.workforce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "leave_type_id")
    private UUID leaveTypeId;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "duration_days")
    private int durationDays;
    @Column(name = "available_balance_at_submit")
    private int availableBalanceAtSubmit;
    @Column(name = "reason")
    private String reason;
    @Column(name = "status")
    private LeaveRequestStatus status;
    @Column(name = "submitted_at")
    private LocalDate submittedAt;
    @Column(name = "review_by_employee_id")
    private UUID reviewByEmployeeId;
    @Column(name = "manager_comment")
    private String managerComment;
    @Column(name = "reviewed_at")
    private LocalDate reviewedAt;

    @OneToOne
    @JoinColumn(name = "leave_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LeaveTypeEntity leaveType;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity employee;
}