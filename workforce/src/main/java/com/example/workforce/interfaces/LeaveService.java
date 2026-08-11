package com.example.workforce.interfaces;

import com.example.workforce.common.PageResult;
import com.example.workforce.model.LeaveRequestCreate;
import com.example.workforce.model.LeaveRequestUpdate;
import com.example.workforce.model.dtos.CalendarDayDto;
import com.example.workforce.model.dtos.HolidayDto;
import com.example.workforce.model.dtos.LeaveBalanceAdjustmentDto;
import com.example.workforce.model.dtos.LeaveBalanceDto;
import com.example.workforce.model.dtos.LeavePolicyDto;
import com.example.workforce.model.dtos.LeaveRequestDto;
import com.example.workforce.model.dtos.LeaveTypeDto;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    PageResult<LeaveRequestDto> listRequests(UUID employeeId, String status, int page, int pageSize);

    LeaveRequestDto createRequest(LeaveRequestCreate request);

    LeaveRequestDto getRequest(UUID requestId);

    LeaveRequestDto updateRequest(UUID requestId, LeaveRequestUpdate request);

    LeaveRequestDto cancelRequest(UUID requestId);

    List<LeaveBalanceDto> balances(UUID employeeId);

    LeaveBalanceDto balance(UUID employeeId, UUID leaveTypeId);

    List<LeaveBalanceAdjustmentDto> adjustments(UUID employeeId, UUID leaveTypeId);

    List<CalendarDayDto> teamCalendar(UUID teamId, int year, int month);

    List<LeaveTypeDto> leaveTypes();

    List<LeavePolicyDto> leavePolicies(UUID leaveTypeId);

    List<HolidayDto> holidays(Integer year);
}