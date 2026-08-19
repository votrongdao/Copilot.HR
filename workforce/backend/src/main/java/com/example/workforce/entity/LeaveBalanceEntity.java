package com.example.workforce.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_balance")
@Data
public class LeaveBalanceEntity {
    private UUID id;
    private UUID employee_id;
    private UUID leave_type_id;
    private int entitlement_amount;
    private int used_amount;
    private int remaining_amount;
    private LocalDateTime updated_at;
}
