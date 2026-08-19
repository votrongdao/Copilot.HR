package com.example.workforce.interfaces;

import com.example.workforce.common.DepartmentDto;
import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.model.TimesheetCorrectionCreate;
import com.example.workforce.model.TimesheetDecision;
import com.example.workforce.model.TimesheetEntryUpdate;
import com.example.workforce.model.dtos.TimesheetCorrectionDto;
import com.example.workforce.model.dtos.TimesheetDetailDto;
import com.example.workforce.model.dtos.TimesheetEntryDto;
import com.example.workforce.model.dtos.TimesheetFilter;
import com.example.workforce.model.dtos.TimesheetListItemDto;
import java.util.List;
import java.util.UUID;

public interface TimesheetService {
    PageResult<TimesheetListItemDto> timesheets(TimesheetFilter filter);

    TimesheetDetailDto timesheet(UUID timesheetId);

    TimesheetDetailDto approve(UUID timesheetId, TimesheetDecision request);

    TimesheetDetailDto reject(UUID timesheetId, TimesheetDecision request);

    List<TimesheetEntryDto> entries(UUID timesheetId);

    TimesheetEntryDto updateEntry(UUID timesheetId, UUID entryId, TimesheetEntryUpdate request);

    List<TimesheetCorrectionDto> corrections(UUID timesheetId);

    TimesheetCorrectionDto createCorrection(UUID timesheetId, TimesheetCorrectionCreate request);

    EmployeeBrief employee(UUID employeeId);

    DepartmentDto department(UUID departmentId);
}