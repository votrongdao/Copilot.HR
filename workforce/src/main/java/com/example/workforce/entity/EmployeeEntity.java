package com.example.workforce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity {
    @Id
    private UUID id;
    @Column(name = "organization_id")
    private UUID organizationId;
    @Column(name = "department_id")
    private UUID departmentId;
    @Column(name = "team_id")
    private UUID teamId;
    @Column(name = "employee_code", nullable = false, length = 50)
    private String employeeCode;
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;
    @Column(name = "job_title", length = 150)
    private String jobTitle;
    @Column(name = "manager_id")
    private UUID managerId;
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
    @Column(name = "employment_status", length = 30)
    private String employmentStatus;
}