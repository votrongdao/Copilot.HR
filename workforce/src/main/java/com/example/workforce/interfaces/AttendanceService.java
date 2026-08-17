package com.example.workforce.interfaces;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.common.ShiftDto;
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
import com.example.workforce.model.dtos.AttendanceExceptionDto;
import com.example.workforce.model.dtos.AttendanceExceptionFilter;
import com.example.workforce.model.dtos.AttendanceExceptionSummaryDto;
import com.example.workforce.model.dtos.AttendanceCorrectionExportFilter;
import com.example.workforce.model.dtos.AttendanceCorrectionFilter;
import com.example.workforce.model.dtos.AttendanceCorrectionResponse;
import com.example.workforce.model.dtos.AttendanceRecordFilter;
import com.example.workforce.model.dtos.AttendanceRecordSummaryDto;
import com.example.workforce.model.dtos.AttendanceRecordDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    AttendanceDashboardSummaryDto dashboardSummary();

    List<AttendanceDashboardClockInDto> recentClockIns();

    String exportDashboard();

    PageResult<AttendanceRecordDto> records(AttendanceRecordFilter filter);

    AttendanceRecordSummaryDto recordSummary(AttendanceRecordFilter filter);

    String exportRecords(AttendanceRecordFilter filter);

    PageResult<AttendanceCorrectionResponse> listCorrections(GetListAttendanceCorrections filter);

    AttendanceCorrectionDetail createCorrection(AttendanceCorrectionCreate request);

    AttendanceCorrectionSummary summary();

    String exportCorrections(AttendanceCorrectionExportFilter filter);

    AttendanceCorrectionDetail getCorrection(UUID correctionId);

    AttendanceCorrectionDetail updateCorrection(UUID correctionId, AttendanceCorrectionUpdate request);

    ReviewStatus reviewStatus(UUID correctionId);

    AttendanceCorrectionDetail approve(UUID correctionId, ReviewDecision request);

    AttendanceCorrectionDetail reject(UUID correctionId, ReviewDecision request);

    List<ReviewHistoryEntry> history(UUID correctionId);

    PageResult<AttendanceExceptionDto> exceptions(AttendanceExceptionFilter filter);

    AttendanceExceptionSummaryDto exceptionSummary();

    List<AttendanceRecordDto> employeeRecords(UUID employeeId, LocalDate dateFrom, LocalDate dateTo);

    AttendanceRecordDto getRecord(UUID recordId);

    List<AttendanceBreakDto> breaks(UUID recordId);

    List<AttendanceCorrectionListItem> employeeCorrections(UUID employeeId);

    List<AttendanceCorrectionListItem> recordCorrections(UUID recordId);

    ShiftDto getShift(UUID shiftId);

    EmployeeBrief getEmployee(UUID employeeId);
}