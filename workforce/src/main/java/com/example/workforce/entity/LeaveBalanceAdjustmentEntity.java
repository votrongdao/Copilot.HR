package com.example.workforce.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_balance_adjustment")
@Data
public class LeaveBalanceAdjustmentEntity {
    private UUID id;
    private UUID leave_balance_id;
    private int previous_amount;
    @Column(nullable = false)
    private int adjustment_amount;

    private int new_amount;
    private String reason;
    private UUID adjusted_by_employee_id;
    private UUID adjusted_at;
}
