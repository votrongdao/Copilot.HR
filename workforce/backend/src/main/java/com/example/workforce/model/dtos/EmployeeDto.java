package com.example.workforce.model.dtos;

import java.util.UUID;

import com.example.workforce.common.DepartmentDto;

public class EmployeeDto {
    private UUID id;
    private String employeeCode;
    private String fullName;
    private String jobTitle;
    private String avatarUrl;
    private ManagerDto manager;
    private DepartmentDto department;
}
