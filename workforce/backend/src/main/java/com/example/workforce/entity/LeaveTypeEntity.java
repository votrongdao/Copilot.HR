package com.example.workforce.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_type")
@Data
public class LeaveTypeEntity {
    private UUID id;
    private UUID organization_id;
    private String name;
    private String status;
}
