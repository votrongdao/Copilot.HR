package com.example.workforce.leave.controller;

import com.example.workforce.common.PageResult;
import com.example.workforce.leave.model.CalendarDayDto;
import com.example.workforce.leave.model.HolidayDto;
import com.example.workforce.leave.model.LeaveBalanceAdjustmentDto;
import com.example.workforce.leave.model.LeaveBalanceDto;
import com.example.workforce.leave.model.LeavePolicyDto;
import com.example.workforce.leave.model.LeaveRequestCreate;
import com.example.workforce.leave.model.LeaveRequestDto;
import com.example.workforce.leave.model.LeaveRequestUpdate;
import com.example.workforce.leave.model.LeaveTypeDto;
import com.example.workforce.interfaces.LeaveService;
import jakarta.validation.Valid;
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
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping("/leave-requests")
    public PageResult<LeaveRequestDto> listRequests(@RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) String status, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        return leaveService.listRequests(employeeId, status, page, pageSize);
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
    public LeaveRequestDto updateRequest(@PathVariable UUID requestId, @RequestBody LeaveRequestUpdate request) {
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
    public List<LeaveBalanceAdjustmentDto> adjustments(@PathVariable UUID employeeId, @PathVariable UUID leaveTypeId) {
        return leaveService.adjustments(employeeId, leaveTypeId);
    }

    @GetMapping("/teams/{teamId}/leave-calendar")
    public List<CalendarDayDto> teamCalendar(@PathVariable UUID teamId, @RequestParam int year,
            @RequestParam int month) {
        return leaveService.teamCalendar(teamId, year, month);
    }

    @GetMapping("/leave-types")
    public List<LeaveTypeDto> leaveTypes() {
        return leaveService.leaveTypes();
    }

    @GetMapping("/leave-policies")
    public List<LeavePolicyDto> leavePolicies(@RequestParam(required = false) UUID leaveTypeId) {
        return leaveService.leavePolicies(leaveTypeId);
    }

    @GetMapping("/holidays")
    public List<HolidayDto> holidays(@RequestParam(required = false) Integer year) {
        return leaveService.holidays(year);
    }
}