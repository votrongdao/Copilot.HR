package com.example.workforce.controller;

import com.example.workforce.interfaces.AttendanceService;
import com.example.workforce.model.AttendanceCorrectionCreate;
import com.example.workforce.model.AttendanceCorrectionDetail;
import com.example.workforce.model.AttendanceCorrectionListItem;
import com.example.workforce.model.AttendanceCorrectionSummary;
import com.example.workforce.model.AttendanceCorrectionUpdate;
import com.example.workforce.model.ReviewDecision;
import com.example.workforce.model.ReviewHistoryEntry;
import com.example.workforce.model.ReviewStatus;
import com.example.workforce.model.dtos.AttendanceBreakDto;
import com.example.workforce.model.dtos.AttendanceRecordDto;
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

    @GetMapping("/attendance-corrections")
    public com.example.workforce.common.PageResult<AttendanceCorrectionListItem> listCorrections(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        return attendanceService.listCorrections(status, employeeId, page, pageSize);
    }

    @PostMapping("/attendance-corrections")
    public ResponseEntity<AttendanceCorrectionDetail> createCorrection(
            @Valid @RequestBody AttendanceCorrectionCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createCorrection(request));
    }

    @GetMapping("/attendance-corrections/summary")
    public AttendanceCorrectionSummary summary() {
        return attendanceService.summary();
    }

    @GetMapping("/attendance-corrections/export")
    public ResponseEntity<byte[]> exportCorrections(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo) {
        byte[] csv = attendanceService.exportCorrections(status, dateFrom, dateTo).getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("attendance-corrections.csv").build().toString())
                .contentType(new MediaType("text", "csv"))
                .body(csv);
    }

    @GetMapping("/attendance-corrections/{correctionId}")
    public AttendanceCorrectionDetail getCorrection(@PathVariable UUID correctionId) {
        return attendanceService.getCorrection(correctionId);
    }

    @PatchMapping("/attendance-corrections/{correctionId}")
    public AttendanceCorrectionDetail updateCorrection(@PathVariable UUID correctionId,
            @RequestBody AttendanceCorrectionUpdate request) {
        return attendanceService.updateCorrection(correctionId, request);
    }

    @GetMapping("/attendance-corrections/{correctionId}/review")
    public ReviewStatus getReviewStatus(@PathVariable UUID correctionId) {
        return attendanceService.reviewStatus(correctionId);
    }

    @PostMapping("/attendance-corrections/{correctionId}/approve")
    public AttendanceCorrectionDetail approve(@PathVariable UUID correctionId,
            @RequestBody(required = false) ReviewDecision request) {
        return attendanceService.approve(correctionId, request == null ? new ReviewDecision(null) : request);
    }

    @PostMapping("/attendance-corrections/{correctionId}/reject")
    public AttendanceCorrectionDetail reject(@PathVariable UUID correctionId, @RequestBody ReviewDecision request) {
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