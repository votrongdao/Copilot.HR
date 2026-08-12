package com.example.workforce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    private UUID id;

    @Column(name = "employee_code", nullable = false, length = 50)
    private String employeeCode;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    protected EmployeeEntity() {
    }

    public EmployeeEntity(UUID id, String employeeCode, String fullName) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFullName() {
        return fullName;
    }
}