package com.example.workforce.interfaces;

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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    PageResult<AttendanceCorrectionListItem> listCorrections(String status, UUID employeeId, int page, int pageSize);

    AttendanceCorrectionDetail createCorrection(AttendanceCorrectionCreate request);

    AttendanceCorrectionSummary summary();

    String exportCorrections(String status, LocalDate dateFrom, LocalDate dateTo);

    AttendanceCorrectionDetail getCorrection(UUID correctionId);

    AttendanceCorrectionDetail updateCorrection(UUID correctionId, AttendanceCorrectionUpdate request);

    ReviewStatus reviewStatus(UUID correctionId);

    AttendanceCorrectionDetail approve(UUID correctionId, ReviewDecision request);

    AttendanceCorrectionDetail reject(UUID correctionId, ReviewDecision request);

    List<ReviewHistoryEntry> history(UUID correctionId);

    List<AttendanceRecordDto> employeeRecords(UUID employeeId, LocalDate dateFrom, LocalDate dateTo);

    AttendanceRecordDto getRecord(UUID recordId);

    List<AttendanceBreakDto> breaks(UUID recordId);

    List<AttendanceCorrectionListItem> employeeCorrections(UUID employeeId);

    List<AttendanceCorrectionListItem> recordCorrections(UUID recordId);

    ShiftDto getShift(UUID shiftId);

    EmployeeBrief getEmployee(UUID employeeId);
}