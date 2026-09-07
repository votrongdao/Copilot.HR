package com.example.workforce.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leave_type")
@Data
public class LeaveTypeEntity {
    private UUID id;
    @Column(name = "organization_id")
    private UUID organizationId;
    private String name;
    private String status;
}
