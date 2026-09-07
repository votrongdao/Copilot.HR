package com.example.workforce.model.dtos;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.model.LeaveRequestCreate;
import com.example.workforce.model.LeaveRequestUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LeaveRequestDto {
        private final UUID id;
        private final EmployeeBrief employee;
        private final LeaveTypeDto leaveType;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final double durationDays;
        private final String reason;
        private final String status;
        private final OffsetDateTime submittedAt;
        private final EmployeeBrief reviewedBy;
        private final String managerComment;
        private final OffsetDateTime reviewedAt;

}
