package com.example.workforce.interfaces.service;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.entity.LeaveBalanceAdjustmentEntity;
import com.example.workforce.entity.LeaveBalanceEntity;
import com.example.workforce.entity.LeaveRequestEntity;
import com.example.workforce.enums.LeaveRequestStatus;
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
import com.example.workforce.repository.ILeaveBalance;
import com.example.workforce.repository.ILeaveBalanceAdjustmentRepository;
import com.example.workforce.repository.ILeaveRequestRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLeaveService implements LeaveService {
    private final ILeaveBalance leavebalanceRepository;
    private final ModelMapper modelMapper;
    private final ILeaveBalanceAdjustmentRepository leaveBalanceAdjustmentRepository;
    private final ILeaveRequestRepository leaveRequestRepository;

    @Override
    public PageResult<LeaveRequestDto> listRequests(LeaveRequestFilter filter) {
        return leaveRequestRepository.findAll().stream()
                .map(this::mapToLeaveRequestDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> new PageResult<>(list, filter.getPage(), filter.getPageSize(), list.size())));
    }

    @Override
    public LeaveRequestDto createRequest(LeaveRequestCreate request) {
        return mapToLeaveRequestDto(leaveRequestRepository.save(mapToLeaveRequestEntity(request)));
    }

    @Override
    public LeaveRequestDto getRequest(UUID requestId) {
        LeaveRequestEntity entity = leaveRequestRepository.findById(requestId).orElse(null);
        return entity != null ? mapToLeaveRequestDto(entity) : null;
    }

    @Override
    public LeaveRequestDto updateRequest(UUID requestId, LeaveRequestUpdate request) {
        LeaveRequestEntity entity = leaveRequestRepository.findById(requestId).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Leave request not found");
        }
        modelMapper.map(request, entity);
        return mapToLeaveRequestDto(leaveRequestRepository.save(entity));
    }

    @Override
    public LeaveRequestDto cancelRequest(UUID requestId) {
        LeaveRequestEntity entity = leaveRequestRepository.findById(requestId).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Leave request not found");
        }
        entity.setStatus(LeaveRequestStatus.CANCELED);
        return mapToLeaveRequestDto(leaveRequestRepository.save(entity));
    }

    @Override
    public List<LeaveBalanceDto> balances(UUID employeeId) {
        List<LeaveBalanceEntity> leaveBalances = leavebalanceRepository.findByEmployeeId(employeeId);
        return leaveBalances.stream().map(this::mapToLeaveBalanceDto).collect(Collectors.toList());
    }

    @Override
    public LeaveBalanceDto balance(UUID employeeId, UUID leaveTypeId) {
        LeaveBalanceEntity entity = leavebalanceRepository.findByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeId);
        return mapToLeaveBalanceDto(entity);
    }

    @Override
    public List<LeaveBalanceAdjustmentDto> adjustments(UUID employeeId, UUID leaveTypeId) {
        return leaveBalanceAdjustmentRepository.findByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeId).stream()
                .map(this::mapToLeaveBalanceAdjustmentDto).collect(Collectors.toList());
    }

    @Override
    public List<CalendarDayDto> teamCalendar(UUID teamId, YearMonth month) {
        return leaveRequestRepository.findAll().stream()
                .map(this::mapToLeaveRequestDto)
                .collect(Collectors.groupingBy(dto -> dto.getStartDate().getDayOfMonth()))
                .entrySet().stream()
                .map(entry -> new CalendarDayDto(
                        LocalDate.of(month.getYear(), month.getMonth(), entry.getKey()),
                        entry.getValue(),
                        (int) entry.getValue().stream().filter(dto -> dto.getStatus() == LeaveRequestStatus.PENDING).count()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveTypeDto> leaveTypes() {
        return null;
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

    public LeaveBalanceDto mapToLeaveBalanceDto(LeaveBalanceEntity entity) {
        return modelMapper.map(entity, LeaveBalanceDto.class);
    }

    private LeaveBalanceAdjustmentDto mapToLeaveBalanceAdjustmentDto(LeaveBalanceAdjustmentEntity entity) {
        return modelMapper.map(entity, LeaveBalanceAdjustmentDto.class);
    }

    private LeaveRequestDto mapToLeaveRequestDto(LeaveRequestEntity entity) {
        return modelMapper.map(entity, LeaveRequestDto.class);
    }

    private LeaveRequestEntity mapToLeaveRequestEntity(LeaveRequestCreate dto) {
        return modelMapper.map(dto, LeaveRequestEntity.class);
    }
}
