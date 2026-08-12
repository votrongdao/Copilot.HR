package com.example.workforce.service;

import com.example.workforce.common.DepartmentDto;
import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.interfaces.TimesheetService;
import com.example.workforce.model.TimesheetCorrectionCreate;
import com.example.workforce.model.TimesheetDecision;
import com.example.workforce.model.TimesheetEntryUpdate;
import com.example.workforce.model.dtos.TimesheetCorrectionDto;
import com.example.workforce.model.dtos.TimesheetDetailDto;
import com.example.workforce.model.dtos.TimesheetEntryDto;
import com.example.workforce.model.dtos.TimesheetListItemDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultTimesheetService implements TimesheetService {
    @Override
    public PageResult<TimesheetListItemDto> timesheets(UUID employeeId, UUID departmentId, String status,
            LocalDate weekStart, int page, int pageSize) {
        return new PageResult<>(List.of(), page, pageSize, 0);
    }

    @Override
    public TimesheetDetailDto timesheet(UUID timesheetId) {
        return TimesheetDetailDto.sample(timesheetId);
    }

    @Override
    public TimesheetDetailDto approve(UUID timesheetId, TimesheetDecision request) {
        return TimesheetDetailDto.sample(timesheetId).withStatus("approved")
                .withReviewedBy(EmployeeBrief.sample(UUID.randomUUID())).withReviewedAt(OffsetDateTime.now());
    }

    @Override
    public TimesheetDetailDto reject(UUID timesheetId, TimesheetDecision request) {
        return TimesheetDetailDto.sample(timesheetId).withStatus("rejected")
                .withReviewedBy(EmployeeBrief.sample(UUID.randomUUID())).withReviewedAt(OffsetDateTime.now());
    }

    @Override
    public List<TimesheetEntryDto> entries(UUID timesheetId) {
        return List.of(TimesheetEntryDto.sample(timesheetId, UUID.randomUUID()));
    }

    @Override
    public TimesheetEntryDto updateEntry(UUID timesheetId, UUID entryId, TimesheetEntryUpdate request) {
        return TimesheetEntryDto.sample(timesheetId, entryId).withUpdate(request);
    }

    @Override
    public List<TimesheetCorrectionDto> corrections(UUID timesheetId) {
        return List.of();
    }

    @Override
    public TimesheetCorrectionDto createCorrection(UUID timesheetId, TimesheetCorrectionCreate request) {
        return TimesheetCorrectionDto.sample(timesheetId, request);
    }

    @Override
    public EmployeeBrief employee(UUID employeeId) {
        return EmployeeBrief.sample(employeeId);
    }

    @Override
    public DepartmentDto department(UUID departmentId) {
        return DepartmentDto.sample(departmentId);
    }
}