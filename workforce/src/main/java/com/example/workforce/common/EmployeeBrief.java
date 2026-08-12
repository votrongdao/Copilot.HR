package com.example.workforce.common;

import java.util.UUID;

public record EmployeeBrief(UUID id, String employeeCode, String fullName, String jobTitle, String avatarUrl,
        UUID departmentId, UUID managerId) {
    public static EmployeeBrief sample(UUID id) {
        String suffix = id.toString().substring(0, 8).toUpperCase();
        return new EmployeeBrief(id, "EMP-" + suffix, "Sample Employee", "Employee", null, null, null);
    }
}