package com.example.workforce.interfaces;

import com.example.workforce.common.DepartmentDto;
import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.timesheet.model.TimesheetCorrectionCreate;
import com.example.workforce.timesheet.model.TimesheetCorrectionDto;
import com.example.workforce.timesheet.model.TimesheetDecision;
import com.example.workforce.timesheet.model.TimesheetDetailDto;
import com.example.workforce.timesheet.model.TimesheetEntryDto;
import com.example.workforce.timesheet.model.TimesheetEntryUpdate;
import com.example.workforce.timesheet.model.TimesheetListItemDto;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimesheetService {
    PageResult<TimesheetListItemDto> timesheets(UUID employeeId, UUID departmentId, String status, LocalDate weekStart,
            int page, int pageSize);

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