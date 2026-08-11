package com.example.workforce.attendance.service;

import com.example.workforce.attendance.model.AttendanceBreakDto;
import com.example.workforce.attendance.model.AttendanceCorrectionCreate;
import com.example.workforce.attendance.model.AttendanceCorrectionDetail;
import com.example.workforce.attendance.model.AttendanceCorrectionListItem;
import com.example.workforce.attendance.model.AttendanceCorrectionSummary;
import com.example.workforce.attendance.model.AttendanceCorrectionUpdate;
import com.example.workforce.attendance.model.AttendanceRecordDto;
import com.example.workforce.attendance.model.ReviewDecision;
import com.example.workforce.attendance.model.ReviewHistoryEntry;
import com.example.workforce.attendance.model.ReviewStatus;
import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.common.ShiftDto;
import com.example.workforce.interfaces.AttendanceService;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultAttendanceService implements AttendanceService {
    @Override
    public PageResult<AttendanceCorrectionListItem> listCorrections(String status, UUID employeeId, int page,
            int pageSize) {
        return new PageResult<>(List.of(), page, pageSize, 0);
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
    public String exportCorrections(String status, LocalDate dateFrom, LocalDate dateTo) {
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
}