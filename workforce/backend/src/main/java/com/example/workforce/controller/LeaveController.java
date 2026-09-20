package com.example.workforce.controller;

import com.example.workforce.common.PageResult;
import com.example.workforce.interfaces.LeaveService;
import com.example.workforce.model.LeaveRequestCreate;
import com.example.workforce.model.LeaveRequestUpdate;
import com.example.workforce.model.dtos.CalendarDayDto;
import com.example.workforce.model.dtos.HolidayDto;
import com.example.workforce.model.dtos.LeaveBalanceAdjustmentDto;
import com.example.workforce.model.dtos.LeaveBalanceDto;
import com.example.workforce.model.dtos.LeavePolicyDto;
import com.example.workforce.model.dtos.LeaveRequestDto;
import com.example.workforce.model.dtos.LeaveRequestFilter;
import com.example.workforce.model.dtos.LeaveTypeDto;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
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
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping("/leave-requests")
    public PageResult<LeaveRequestDto> listRequests(
            @RequestParam(name = "employee_id", required = false) UUID employeeId,
            @RequestParam(name = "leave_type_id", required = false) UUID leaveTypeId,
            @RequestParam(required = false) String status,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return leaveService.listRequests(
                new LeaveRequestFilter(employeeId, leaveTypeId, status, dateFrom, dateTo, page, pageSize));
    }

    @PostMapping("/leave-requests")
    public ResponseEntity<LeaveRequestDto> createRequest(@Valid @RequestBody LeaveRequestCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.createRequest(request));
    }

    @GetMapping("/leave-requests/{requestId}")
    public LeaveRequestDto getRequest(@PathVariable UUID requestId) {
        return leaveService.getRequest(requestId);
    }

    @PatchMapping("/leave-requests/{requestId}")
    public LeaveRequestDto updateRequest(@PathVariable UUID requestId,
            @Valid @RequestBody LeaveRequestUpdate request) {
        return leaveService.updateRequest(requestId, request);
    }

    @PostMapping("/leave-requests/{requestId}/cancel")
    public LeaveRequestDto cancelRequest(@PathVariable UUID requestId) {
        return leaveService.cancelRequest(requestId);
    }

    @GetMapping("/employees/{employeeId}/leave-balances")
    public List<LeaveBalanceDto> balances(@PathVariable UUID employeeId) {
        return leaveService.balances(employeeId);
    }

    @GetMapping("/employees/{employeeId}/leave-balances/{leaveTypeId}")
    public LeaveBalanceDto balance(@PathVariable UUID employeeId, @PathVariable UUID leaveTypeId) {
        return leaveService.balance(employeeId, leaveTypeId);
    }

    @GetMapping("/employees/{employeeId}/leave-balances/{leaveTypeId}/adjustments")
    public List<LeaveBalanceAdjustmentDto> adjustments(@PathVariable UUID employeeId,
            @PathVariable UUID leaveTypeId) {
        return leaveService.adjustments(employeeId, leaveTypeId);
    }

    @GetMapping("/teams/{teamId}/leave-calendar")
    public List<CalendarDayDto> teamCalendar(@PathVariable UUID teamId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return leaveService.teamCalendar(teamId, month);
    }

    @GetMapping("/leave-types")
    public List<LeaveTypeDto> leaveTypes() {
        return leaveService.leaveTypes();
    }

    @GetMapping("/leave-policies")
    public List<LeavePolicyDto> leavePolicies(
            @RequestParam(name = "leave_type_id", required = false) UUID leaveTypeId) {
        return leaveService.leavePolicies(leaveTypeId);
    }

    @GetMapping("/holidays")
    public List<HolidayDto> holidays(
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo) {
        if (dateFrom != null && dateTo != null && dateTo.isBefore(dateFrom)) {
            throw new IllegalArgumentException("date_to must be on or after date_from");
        }
        return leaveService.holidays(dateFrom, dateTo);
    }
}
