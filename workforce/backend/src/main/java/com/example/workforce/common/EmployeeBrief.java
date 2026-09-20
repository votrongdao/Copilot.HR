package com.example.workforce.common;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class EmployeeBrief {
    private UUID id;
    private String employeeCode;
    private String fullName;
    private String jobTitle;
    private String avatarUrl;
    private UUID departmentId;
    private UUID managerId;

    public static EmployeeBrief sample(UUID id) {
        String suffix = id.toString().substring(0, 8).toUpperCase();
        return new EmployeeBrief(id, "EMP-" + suffix, "Sample Employee", "Employee", null, null, null);
    }
}