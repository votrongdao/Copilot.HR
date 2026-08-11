package com.example.workforce.leave.model;

import java.util.UUID;

public record CalendarLeaveDto(UUID requestId, UUID employeeId, String employeeName, UUID leaveTypeId, String status) {
}