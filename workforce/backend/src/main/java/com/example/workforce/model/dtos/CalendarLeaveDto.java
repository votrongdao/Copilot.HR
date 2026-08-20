package com.example.workforce.model.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarLeaveDto {
    private UUID requestId;
    private EmployeeDto employeeId;
    private LeaveTypeDto leaveType;
    private String status;
}