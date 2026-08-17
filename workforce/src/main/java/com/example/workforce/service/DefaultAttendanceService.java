package com.example.workforce.service;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.common.ShiftDto;
import com.example.workforce.entity.AttendanceCorrectionEntity;
import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.interfaces.AttendanceService;
import com.example.workforce.model.AttendanceCorrectionCreate;
import com.example.workforce.model.AttendanceCorrectionDetail;
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
import com.example.workforce.model.dtos.AttendanceCorrectionFilter;
import com.example.workforce.model.dtos.AttendanceCorrectionResponse;
import com.example.workforce.model.dtos.AttendanceExceptionDto;
import com.example.workforce.model.dtos.AttendanceExceptionFilter;
import com.example.workforce.model.dtos.AttendanceExceptionSummaryDto;
import com.example.workforce.model.dtos.AttendanceRecordFilter;
import com.example.workforce.model.dtos.AttendanceRecordSummaryDto;
import com.example.workforce.repository.inmemory.IInmemoryAttendanceCorrectionRepository;

import lombok.AllArgsConstructor;

import com.example.workforce.model.dtos.AttendanceRecordDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultAttendanceService implements AttendanceService {
    // private final IAttendanceRecordRepository attendanceRecordRepository;

    private final IInmemoryAttendanceCorrectionRepository InmemoryAttendanceCorrectionRepository;

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
        var result = InmemoryAttendanceCorrectionRepository.findAll(filter).stream();
        return new PageResult<>(
                result.map(this::mapToAttendanceCorrectionResponse)
                .collect(Collectors.toList()), filter.getPage(), filter.getPageSize(), 1);
    }

    @Override
    public AttendanceCorrectionDetail createCorrection(AttendanceCorrectionCreate request) {
        UUID employeeId = UUID.randomUUID();
        return AttendanceCorrectionDetail.sample(UUID.randomUUID(), EmployeeBrief.sample(employeeId), request);
    }

    @Override
    public AttendanceCorrectionSummary summary() {
        return new AttendanceCorrectionSummary(0, 0, 0);
    }

    @Override
    public String exportCorrections(AttendanceCorrectionExportFilter filter) {
        return "id,employee_id,status,submitted_at\n";
    }

    @Override
    public AttendanceCorrectionDetail getCorrection(UUID correctionId) {
        return AttendanceCorrectionDetail.sample(correctionId);
    }

    @Override
    public AttendanceCorrectionDetail updateCorrection(UUID correctionId, AttendanceCorrectionUpdate request) {
        AttendanceCorrectionDetail detail = AttendanceCorrectionDetail.sample(correctionId);
        return detail.withManagerComment(request.managerComment());
    }

    @Override
    public ReviewStatus reviewStatus(UUID correctionId) {
        return new ReviewStatus(correctionId, "pending", null, null, null);
    }

    @Override
    public AttendanceCorrectionDetail approve(UUID correctionId, ReviewDecision request) {
        return AttendanceCorrectionDetail.sample(correctionId).withStatus("approved")
                .withManagerComment(request.managerComment()).withReviewedBy(EmployeeBrief.sample(UUID.randomUUID()))
                .withReviewedAt(OffsetDateTime.now());
    }

    @Override
    public AttendanceCorrectionDetail reject(UUID correctionId, ReviewDecision request) {
        return AttendanceCorrectionDetail.sample(correctionId).withStatus("rejected")
                .withManagerComment(request.managerComment()).withReviewedBy(EmployeeBrief.sample(UUID.randomUUID()))
                .withReviewedAt(OffsetDateTime.now());
    }

    @Override
    public List<ReviewHistoryEntry> history(UUID correctionId) {
        return List.of();
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
        return List.of();
    }

    @Override
    public List<AttendanceCorrectionListItem> recordCorrections(UUID recordId) {
        return List.of();
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
}