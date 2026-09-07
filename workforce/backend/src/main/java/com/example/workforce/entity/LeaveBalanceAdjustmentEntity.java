package com.example.workforce.entity;

import java.security.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_balance_adjustment")
@Data
public class LeaveBalanceAdjustmentEntity {
    private UUID id;
    @Column(name = "leave_balance_id")
    private UUID leaveBalanceId;
    @Column(name = "previous_amount")
    private int previousAmount;
    @Column(name = "adjustment_amount", nullable = false)
    private int adjustmentAmount;
    @Column(name = "new_amount")
    private int newAmount;
    @Column(name = "reason")
    private String reason;
    @Column(name = "adjusted_by")
    private UUID adjustedBy;
    @Column(name = "adjusted_at")
    private Timestamp adjustedAt;

    @ManyToOne
    @JoinColumn(name = "employee", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity employee;
    @OneToOne
    @JoinColumn(name = "leave_balance", referencedColumnName = "id", insertable = false, updatable = false)
    private LeaveBalanceEntity leaveBalance;
}
