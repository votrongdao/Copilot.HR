package com.example.workforce.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_balance")
@Data
public class LeaveBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "employee_id")
    private UUID employeeId;
    @Column(name = "leave_type_id")
    private UUID leaveTypeId;
    @Column(name = "entitlement_amount")
    private int entitlementAmount;
    @Column(name = "used_amount")
    private int usedAmount;
    @Column(name = "remaining_amount")
    private int remainingAmount;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "reset_date")
    private LocalDateTime resetDate;
    @OneToOne
    @JoinColumn(name = "leave_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LeaveTypeEntity leaveType;
}
