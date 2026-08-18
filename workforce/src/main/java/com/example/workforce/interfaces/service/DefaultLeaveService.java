package com.example.workforce.interfaces.service;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.entity.LeaveRequestEntity;
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
import com.example.workforce.repository.inmemory.ILeaveRequestRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultLeaveService implements LeaveService {
    private final ILeaveRequestRepository leaveRequestRepository;

    public DefaultLeaveService(ILeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Override
    public PageResult<LeaveRequestDto> listRequests(LeaveRequestFilter filter) {
        List<LeaveRequestEntity> leaveRequests = leaveRequestRepository.findAll(filter);
        return new PageResult<>(leaveRequests.stream().map(LeaveRequestDto::fromEntity).toList(), filter.getPage(), filter.getPageSize(), leaveRequests.size());
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
    public List<CalendarDayDto> teamCalendar(UUID teamId, YearMonth month) {
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
    public List<HolidayDto> holidays(LocalDate dateFrom, LocalDate dateTo) {
        int year = dateFrom != null ? dateFrom.getYear() : LocalDate.now().getYear();
        return List.of(new HolidayDto(UUID.randomUUID(), "New Year",
                LocalDate.of(year, 1, 1), "public", "global", "active"));
    }
}
