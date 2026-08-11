package com.example.workforce.leave.service;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.interfaces.LeaveService;
import com.example.workforce.leave.model.CalendarDayDto;
import com.example.workforce.leave.model.HolidayDto;
import com.example.workforce.leave.model.LeaveBalanceAdjustmentDto;
import com.example.workforce.leave.model.LeaveBalanceDto;
import com.example.workforce.leave.model.LeavePolicyDto;
import com.example.workforce.leave.model.LeaveRequestCreate;
import com.example.workforce.leave.model.LeaveRequestDto;
import com.example.workforce.leave.model.LeaveRequestUpdate;
import com.example.workforce.leave.model.LeaveTypeDto;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultLeaveService implements LeaveService {
    @Override
    public PageResult<LeaveRequestDto> listRequests(UUID employeeId, String status, int page, int pageSize) {
        return new PageResult<>(List.of(), page, pageSize, 0);
    }

    @Override
    public LeaveRequestDto createRequest(LeaveRequestCreate request) {
        UUID employeeUuid = request.employeeId() != null ? request.employeeId() : UUID.randomUUID();
        return LeaveRequestDto.sample(UUID.randomUUID(), EmployeeBrief.sample(employeeUuid), request);
    }

    @Override
    public LeaveRequestDto getRequest(UUID requestId) {
        return LeaveRequestDto.sample(requestId);
    }

    @Override
    public LeaveRequestDto updateRequest(UUID requestId, LeaveRequestUpdate request) {
        return LeaveRequestDto.sample(requestId).withUpdate(request);
    }

    @Override
    public LeaveRequestDto cancelRequest(UUID requestId) {
        return LeaveRequestDto.sample(requestId).withStatus("cancelled");
    }

    @Override
    public List<LeaveBalanceDto> balances(UUID employeeId) {
        return List.of(LeaveBalanceDto.sample(UUID.randomUUID(), employeeId));
    }

    @Override
    public LeaveBalanceDto balance(UUID employeeId, UUID leaveTypeId) {
        return LeaveBalanceDto.sample(UUID.randomUUID(), employeeId, leaveTypeId);
    }

    @Override
    public List<LeaveBalanceAdjustmentDto> adjustments(UUID employeeId, UUID leaveTypeId) {
        return List.of();
    }

    @Override
    public List<CalendarDayDto> teamCalendar(UUID teamId, int year, int month) {
        return List.of();
    }

    @Override
    public List<LeaveTypeDto> leaveTypes() {
        return List.of(new LeaveTypeDto(UUID.randomUUID(), "Annual Leave", "active"));
    }

    @Override
    public List<LeavePolicyDto> leavePolicies(UUID leaveTypeId) {
        return List.of(LeavePolicyDto.sample(UUID.randomUUID(), leaveTypeId != null ? leaveTypeId : UUID.randomUUID()));
    }

    @Override
    public List<HolidayDto> holidays(Integer year) {
        return List.of(new HolidayDto(UUID.randomUUID(), "New Year",
                LocalDate.of(year != null ? year : LocalDate.now().getYear(), 1, 1), "public", "global", "active"));
    }
}