package com.example.workforce.interfaces.service;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.common.ShiftDto;
import com.example.workforce.entity.AttendanceCorrectionEntity;
import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.entity.EmployeeEntity;
import com.example.workforce.enums.AttendanceCorrectionStatus;
import com.example.workforce.interfaces.AttendanceService;
import com.example.workforce.model.AttendanceCorrectionCreate;
import com.example.workforce.model.AttendanceCorrectionListItem;
import com.example.workforce.model.AttendanceCorrectionSummary;
import com.example.workforce.model.AttendanceCorrectionUpdate;
import com.example.workforce.model.GetListAttendanceCorrections;
import com.example.workforce.model.ReviewDecision;
import com.example.workforce.model.ReviewHistoryEntry;
import com.example.workforce.model.ReviewStatus;
import com.example.workforce.model.dtos.AttendanceDashboardClockInDto;
import com.example.workforce.model.dtos.AttendanceDashboardSummaryDto;
import com.example.workforce.model.dtos.AttendanceBreakDto;
import com.example.workforce.model.dtos.AttendanceCorrectionExportFilter;
import com.example.workforce.model.dtos.AttendanceCorrectionResponse;
import com.example.workforce.model.dtos.AttendanceExceptionDto;
import com.example.workforce.model.dtos.AttendanceExceptionFilter;
import com.example.workforce.model.dtos.AttendanceExceptionSummaryDto;
import com.example.workforce.model.dtos.AttendanceRecordFilter;
import com.example.workforce.model.dtos.AttendanceRecordSummaryDto;
import com.example.workforce.repository.IAttendanceCorrectionRepository;
import com.example.workforce.repository.IAttendanceRecordRepository;
import com.example.workforce.repository.IEmployeeRepository;

import lombok.RequiredArgsConstructor;

import com.example.workforce.model.dtos.AttendanceRecordDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAttendanceService implements AttendanceService {
    // private final IAttendanceRecordRepository attendanceRecordRepository;

    private final IAttendanceCorrectionRepository attendanceCorrectionRepository;
    private final IEmployeeRepository employeeRepository;
    private final IAttendanceRecordRepository attendanceRecordRepository;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDashboardSummaryDto dashboardSummary() {
        return new AttendanceDashboardSummaryDto(128, 104, 18);
    }

    @Override
    public List<AttendanceDashboardClockInDto> recentClockIns() {
        UUID employeeId = UUID.randomUUID();
        return List.of(
                new AttendanceDashboardClockInDto(UUID.randomUUID(), EmployeeBrief.sample(employeeId),
                        OffsetDateTime.now().minusMinutes(15), UUID.randomUUID(), "present"),
                new AttendanceDashboardClockInDto(UUID.randomUUID(), EmployeeBrief.sample(UUID.randomUUID()),
                        OffsetDateTime.now().minusMinutes(40), UUID.randomUUID(), "late"));
    }

    @Override
    public String exportDashboard() {
        return "metric,value\ntotal_clock_ins,128\non_time_clock_ins,104\nlate_clock_ins,18\nmissing_clock_outs,6\n";
    }

    @Override
    public PageResult<AttendanceRecordDto> records(AttendanceRecordFilter filter) {
        UUID employeeId = filter.employeeId() != null ? filter.employeeId() : UUID.randomUUID();
        return new PageResult<>(List.of(AttendanceRecordDto.sample(UUID.randomUUID(), employeeId)), filter.page(),
                filter.pageSize(), 1);
    }

    @Override
    public AttendanceRecordSummaryDto recordSummary(AttendanceRecordFilter filter) {
        return new AttendanceRecordSummaryDto(42, 32, 6, 4);
    }

    @Override
    public String exportRecords(AttendanceRecordFilter filter) {
        return "id,employee_id,attendance_date,status\n";
    }

    @Override
    public PageResult<AttendanceCorrectionResponse> listCorrections(GetListAttendanceCorrections filter) {
        var result = attendanceCorrectionRepository.findAll(filter).stream();
        return new PageResult<>(
                result.map(this::mapToAttendanceCorrectionResponse)
                        .collect(Collectors.toList()),
                filter.getPage(), filter.getPageSize(), 1);
    }

    @Override
    public AttendanceCorrectionResponse createCorrection(AttendanceCorrectionCreate request) {
        var entity = mapToAttendanceEntity(request);
        return mapToAttendanceCorrectionResponse(attendanceCorrectionRepository.save(entity));
    }

    @Override
    public AttendanceCorrectionSummary summary() {
        return attendanceCorrectionRepository.getAttendanceCorrectionSummary();
    }

    @Override
    public String exportCorrections(AttendanceCorrectionExportFilter filter) {
        return "id,employee_id,status,submitted_at\n";
    }

    @Override
    public AttendanceCorrectionResponse getCorrection(UUID correctionId) {
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance correction not found"));
        return mapToAttendanceCorrectionResponse(entity);
    }

    @Override
    public AttendanceCorrectionResponse updateCorrection(UUID correctionId, AttendanceCorrectionUpdate request) {
        // Update the entity with the new values from the request
        // This is a simplified example - you would need to implement the actual update
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance correction not found"));
        entity.setManagerComment(request.managerComment());
        // save to inmemory
        attendanceCorrectionRepository.save(entity);
        // logic
        return mapToAttendanceCorrectionResponse(entity);
    }

    @Override
    public ReviewStatus reviewStatus(UUID correctionId) {
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance Corrections not found"));
        entity.setStatus(AttendanceCorrectionStatus.PENDING);
        entity.setReviewedAt(OffsetDateTime.now());
        entity.setReviewedByEmployeeId(UUID.randomUUID());
        attendanceCorrectionRepository.save(entity);
        return new ReviewStatus(entity.getStatus().toString(), entity.getReviewedAt(),
                EmployeeBrief.sample(entity.getReviewedByEmployeeId()));
    }

    @Override
    public AttendanceCorrectionResponse approve(UUID correctionId, ReviewDecision request) {
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance correction not found"));
        entity.setStatus(AttendanceCorrectionStatus.APPROVED);
        entity.setManagerComment(request.managerComment());
        attendanceCorrectionRepository.save(entity);
        return mapToAttendanceCorrectionResponse(entity);
    }

    @Override
    public AttendanceCorrectionResponse reject(UUID correctionId, ReviewDecision request) {
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance correction not found"));
        entity.setStatus(AttendanceCorrectionStatus.REJECTED);
        entity.setManagerComment(request.managerComment());
        attendanceCorrectionRepository.save(entity);
        return mapToAttendanceCorrectionResponse(entity);
    }

    @Override
    public List<ReviewHistoryEntry> history(UUID correctionId) {
        AttendanceCorrectionEntity entity = attendanceCorrectionRepository.findById(correctionId)
                .orElseThrow(() -> new RuntimeException("Attendance correction not found"));
        return List.of(new ReviewHistoryEntry(UUID.randomUUID(), "Created",
                EmployeeBrief.sample(entity.getEmployeeId()), "Initial submission", OffsetDateTime.now()));
    }

    @Override
    public PageResult<AttendanceExceptionDto> exceptions(AttendanceExceptionFilter filter) {
        return new PageResult<>(List.of(
                AttendanceExceptionDto.sample(UUID.randomUUID(), filter.employeeId() != null ? filter.employeeId()
                        : UUID.randomUUID())),
                filter.page(), filter.pageSize(), 1);
    }

    @Override
    public AttendanceExceptionSummaryDto exceptionSummary() {
        return new AttendanceExceptionSummaryDto(12, 7, 5);
    }

    @Override
    public List<AttendanceRecordDto> employeeRecords(UUID employeeId, LocalDate dateFrom, LocalDate dateTo) {
        return List.of(AttendanceRecordDto.sample(UUID.randomUUID(), employeeId));
    }

    @Override
    public AttendanceRecordDto getRecord(UUID recordId) {
        return AttendanceRecordDto.sample(recordId, UUID.randomUUID());
    }

    @Override
    public List<AttendanceBreakDto> breaks(UUID recordId) {
        return List.of(AttendanceBreakDto.sample(UUID.randomUUID(), recordId));
    }

    @Override
    public List<AttendanceCorrectionListItem> employeeCorrections(UUID employeeId) {
        List<AttendanceCorrectionEntity> corrections = attendanceCorrectionRepository.findByEmployeeId(employeeId);
        return corrections.stream().map(this::mapToAttendanceCorrectionListItem).toList();
    }

    @Override
    public List<AttendanceCorrectionListItem> recordCorrections(UUID recordId) {
        List<AttendanceCorrectionEntity> corrections = attendanceCorrectionRepository.findByRecordId(recordId);
        return corrections.stream().map(this::mapToAttendanceCorrectionListItem).toList();
    }

    @Override
    public ShiftDto getShift(UUID shiftId) {
        return ShiftDto.sample(shiftId);
    }

    @Override
    public EmployeeBrief getEmployee(UUID employeeId) {
        return EmployeeBrief.sample(employeeId);
    }

    private AttendanceCorrectionResponse mapToAttendanceCorrectionResponse(AttendanceCorrectionEntity recordDto) {
        return modelMapper.map(recordDto, AttendanceCorrectionResponse.class);
    }

    private AttendanceCorrectionEntity mapToAttendanceEntity(AttendanceCorrectionCreate request) {
        return modelMapper.map(request, AttendanceCorrectionEntity.class);
    }

    private AttendanceCorrectionListItem mapToAttendanceCorrectionListItem(
            AttendanceCorrectionEntity entity,
            EmployeeBrief employee,
            LocalDate attendanceDate) {

        AttendanceCorrectionListItem item = new AttendanceCorrectionListItem();

        item.setId(entity.getId());
        item.setEmployee(employee);
        item.setAttendanceDate(attendanceDate);
        item.setReasonCategory(entity.getReasonCategory());
        item.setStatus(entity.getStatus().name());

        return item;
    }

    private AttendanceCorrectionListItem mapToAttendanceCorrectionListItem(
            AttendanceCorrectionEntity entity) {

        EmployeeEntity employee = employeeRepository.findById(entity.getEmployeeId()).orElse(null);

        AttendanceRecordEntity attendanceRecord = attendanceRecordRepository.findById(entity.getAttendanceRecordId())
                .orElse(null);

        EmployeeBrief employeeBrief = new EmployeeBrief(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getJobTitle(),
                null,
                employee.getDepartmentId(),
                employee.getManagerId());

        return new AttendanceCorrectionListItem(
                entity.getId(),
                employeeBrief,
                attendanceRecord.getAttendanceDate(),
                entity.getReasonCategory(),
                entity.getStatus().name(),
                entity.getSubmittedAt());
    }
}