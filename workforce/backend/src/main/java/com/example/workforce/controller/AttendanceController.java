package com.example.workforce.controller;

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
import com.example.workforce.model.dtos.AttendanceRecordDto;
import com.example.workforce.common.PageResult;
import com.example.workforce.common.ShiftDto;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendance/dashboard/summary")
    public AttendanceDashboardSummaryDto dashboardSummary() {
        return attendanceService.dashboardSummary();
    }

    @GetMapping("/attendance/dashboard/recent-clock-ins")
    public List<AttendanceDashboardClockInDto> recentClockIns() {
        return attendanceService.recentClockIns();
    }

    @GetMapping("/attendance/dashboard/export")
    public ResponseEntity<byte[]> exportDashboard() {
        byte[] csv = attendanceService.exportDashboard().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("attendance-dashboard.csv").build().toString())
                .contentType(new MediaType("text", "csv"))
                .body(csv);
    }

    @GetMapping("/attendance-records")
    public PageResult<AttendanceRecordDto> records(
            @RequestParam(name = "employee_id", required = false) UUID employeeId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return attendanceService.records(
                new AttendanceRecordFilter(employeeId, status, dateFrom, dateTo, page, pageSize));
    }

    @GetMapping("/attendance-records/summary")
    public AttendanceRecordSummaryDto recordSummary(
            @RequestParam(name = "employee_id", required = false) UUID employeeId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo) {
        return attendanceService.recordSummary(new AttendanceRecordFilter(employeeId, status, dateFrom, dateTo, 1,
                20));
    }

    @GetMapping("/attendance-records/export")
    public ResponseEntity<byte[]> exportRecords(
            @RequestParam(name = "employee_id", required = false) UUID employeeId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo) {
        AttendanceRecordFilter filter = new AttendanceRecordFilter(employeeId, status, dateFrom, dateTo, 1, 20);
        byte[] csv = attendanceService.exportRecords(filter).getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("attendance-records.csv").build().toString())
                .contentType(new MediaType("text", "csv"))
                .body(csv);
    }

    @GetMapping("/attendance-corrections")
    public PageResult<AttendanceCorrectionResponse> listCorrections(
            @ModelAttribute GetListAttendanceCorrections request) {
        return attendanceService.listCorrections(request);
    }

    @PostMapping("/attendance-corrections")
    public ResponseEntity<AttendanceCorrectionResponse> createCorrection(
            @Valid @RequestBody AttendanceCorrectionCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createCorrection(request));
    }

    @GetMapping("/attendance-corrections/summary")
    public AttendanceCorrectionSummary summary() {
        return attendanceService.summary();
    }

    @GetMapping("/attendance-exceptions")
    public PageResult<AttendanceExceptionDto> exceptions(
            @RequestParam(name = "employee_id", required = false) UUID employeeId,
            @RequestParam(name = "exception_type", required = false) String exceptionType,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return attendanceService.exceptions(
                new AttendanceExceptionFilter(employeeId, exceptionType, dateFrom, dateTo, page, pageSize));
    }

    @GetMapping("/attendance-exceptions/summary")
    public AttendanceExceptionSummaryDto exceptionSummary() {
        return attendanceService.exceptionSummary();
    }

    @GetMapping("/attendance-corrections/export")
    public ResponseEntity<byte[]> exportCorrections(
            @RequestParam(required = false) String status,
            @RequestParam(name = "date_from", required = false) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) LocalDate dateTo) {
        AttendanceCorrectionExportFilter filter = new AttendanceCorrectionExportFilter(status, dateFrom, dateTo);
        byte[] csv = attendanceService.exportCorrections(filter).getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("attendance-corrections.csv").build().toString())
                .contentType(new MediaType("text", "csv"))
                .body(csv);
    }

    @GetMapping("/attendance-corrections/{correctionId}")
    public AttendanceCorrectionResponse getCorrection(@PathVariable UUID correctionId) {
        return attendanceService.getCorrection(correctionId);
    }

    @PatchMapping("/attendance-corrections/{correctionId}")
    public AttendanceCorrectionResponse updateCorrection(@PathVariable UUID correctionId,
            @RequestBody AttendanceCorrectionUpdate request) {
        return attendanceService.updateCorrection(correctionId, request);
    }

    @GetMapping("/attendance-corrections/{correctionId}/review")
    public ReviewStatus getReviewStatus(@PathVariable UUID correctionId) {
        return attendanceService.reviewStatus(correctionId);
    }

    @PostMapping("/attendance-corrections/{correctionId}/approve")
    public AttendanceCorrectionResponse approve(@PathVariable UUID correctionId,
            @RequestBody(required = false) ReviewDecision request) {
        return attendanceService.approve(correctionId, request == null ? new ReviewDecision(null) : request);
    }

    @PostMapping("/attendance-corrections/{correctionId}/reject")
    public AttendanceCorrectionResponse reject(@PathVariable UUID correctionId, @RequestBody ReviewDecision request) {
        return attendanceService.reject(correctionId, request);
    }

    @GetMapping("/attendance-corrections/{correctionId}/review/history")
    public List<ReviewHistoryEntry> history(@PathVariable UUID correctionId) {
        return attendanceService.history(correctionId);
    }

    @GetMapping("/employees/{employeeId}/attendance-records")
    public List<AttendanceRecordDto> employeeRecords(
            @PathVariable UUID employeeId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo) {
        return attendanceService.employeeRecords(employeeId, dateFrom, dateTo);
    }

    @GetMapping("/attendance-records/{recordId}")
    public AttendanceRecordDto getRecord(@PathVariable UUID recordId) {
        return attendanceService.getRecord(recordId);
    }

    @GetMapping("/attendance-records/{recordId}/breaks")
    public List<AttendanceBreakDto> breaks(@PathVariable UUID recordId) {
        return attendanceService.breaks(recordId);
    }

    @GetMapping("/employees/{employeeId}/attendance-corrections")
    public List<AttendanceCorrectionListItem> employeeCorrections(@PathVariable UUID employeeId) {
        return attendanceService.employeeCorrections(employeeId);
    }

    @GetMapping("/attendance-records/{recordId}/corrections")
    public List<AttendanceCorrectionListItem> recordCorrections(@PathVariable UUID recordId) {
        return attendanceService.recordCorrections(recordId);
    }

    @GetMapping("/shifts/{shiftId}")
    public ShiftDto getShift(@PathVariable UUID shiftId) {
        return attendanceService.getShift(shiftId);
    }
}
