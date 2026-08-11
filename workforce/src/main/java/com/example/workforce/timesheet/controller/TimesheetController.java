package com.example.workforce.timesheet.controller;

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
import com.example.workforce.interfaces.TimesheetService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimesheetController {
    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping("/timesheets")
    public PageResult<TimesheetListItemDto> timesheets(@RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) UUID departmentId, @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate weekStart, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        return timesheetService.timesheets(employeeId, departmentId, status, weekStart, page, pageSize);
    }

    @GetMapping("/timesheets/{timesheetId}")
    public TimesheetDetailDto timesheet(@PathVariable UUID timesheetId) {
        return timesheetService.timesheet(timesheetId);
    }

    @PostMapping("/timesheets/{timesheetId}/approve")
    public TimesheetDetailDto approve(@PathVariable UUID timesheetId,
            @RequestBody(required = false) TimesheetDecision request) {
        return timesheetService.approve(timesheetId, request == null ? new TimesheetDecision(null) : request);
    }

    @PostMapping("/timesheets/{timesheetId}/reject")
    public TimesheetDetailDto reject(@PathVariable UUID timesheetId, @RequestBody TimesheetDecision request) {
        return timesheetService.reject(timesheetId, request);
    }

    @GetMapping("/timesheets/{timesheetId}/entries")
    public List<TimesheetEntryDto> entries(@PathVariable UUID timesheetId) {
        return timesheetService.entries(timesheetId);
    }

    @PatchMapping("/timesheets/{timesheetId}/entries/{entryId}")
    public TimesheetEntryDto updateEntry(@PathVariable UUID timesheetId, @PathVariable UUID entryId,
            @RequestBody TimesheetEntryUpdate request) {
        return timesheetService.updateEntry(timesheetId, entryId, request);
    }

    @GetMapping("/timesheets/{timesheetId}/corrections")
    public List<TimesheetCorrectionDto> corrections(@PathVariable UUID timesheetId) {
        return timesheetService.corrections(timesheetId);
    }

    @PostMapping("/timesheets/{timesheetId}/corrections")
    public ResponseEntity<TimesheetCorrectionDto> createCorrection(@PathVariable UUID timesheetId,
            @Valid @RequestBody TimesheetCorrectionCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheetService.createCorrection(timesheetId, request));
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeBrief employee(@PathVariable UUID employeeId) {
        return timesheetService.employee(employeeId);
    }

    @GetMapping("/departments/{departmentId}")
    public DepartmentDto department(@PathVariable UUID departmentId) {
        return timesheetService.department(departmentId);
    }
}