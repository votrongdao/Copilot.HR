package com.example.workforce.model.dtos;

import java.util.UUID;

public record CalendarLeaveDto(UUID requestId, UUID employeeId, String employeeName, UUID leaveTypeId, String status) {
}
