package com.example.workforce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.workforce.common.EmployeeBrief;
import com.example.workforce.common.PageResult;
import com.example.workforce.interfaces.AttendanceService;
import com.example.workforce.model.AttendanceCorrectionSummary;
import com.example.workforce.model.dtos.AttendanceDashboardClockInDto;
import com.example.workforce.model.dtos.AttendanceDashboardSummaryDto;
import com.example.workforce.model.dtos.AttendanceExceptionFilter;
import com.example.workforce.model.dtos.AttendanceExceptionSummaryDto;
import com.example.workforce.model.dtos.AttendanceRecordFilter;
import com.example.workforce.model.dtos.AttendanceRecordSummaryDto;
import com.example.workforce.model.dtos.AttendanceRecordDto;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AttendanceController.class)
class AttendanceControllerTest {
    private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AttendanceService attendanceService;

    @Test
    void getDashboardSummary_shouldReturnSummary_whenRequestIsValid() throws Exception {
        when(attendanceService.dashboardSummary()).thenReturn(new AttendanceDashboardSummaryDto(2405, 18, 42));

        mockMvc.perform(get("/attendance/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeWorkforce").value(2405))
                .andExpect(jsonPath("$.geofenceAlerts").value(18))
                .andExpect(jsonPath("$.lateArrivals").value(42));
        verify(attendanceService).dashboardSummary();
    }

    @Test
    void getRecentClockIns_shouldReturnRecentClockIns_whenDataExists() throws Exception {
        when(attendanceService.recentClockIns()).thenReturn(List.of(
                new AttendanceDashboardClockInDto(ID, EmployeeBrief.sample(ID),
                        OffsetDateTime.parse("2026-08-12T08:10:00Z"), ID, "present")));

        mockMvc.perform(get("/attendance/dashboard/recent-clock-ins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("present"));
    }

    @Test
    void exportDashboard_shouldReturnCsvHeaders() throws Exception {
        when(attendanceService.exportDashboard()).thenReturn("metric,value\n");

        mockMvc.perform(get("/attendance/dashboard/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/csv"))
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("attendance-dashboard.csv")))
                .andExpect(content().bytes("metric,value\n".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void records_shouldBindOpenApiSnakeCaseParameters() throws Exception {
        when(attendanceService.records(any())).thenReturn(new PageResult<>(List.of(
                new AttendanceRecordDto(ID, ID, ID, LocalDate.parse("2026-08-12"),
                        OffsetDateTime.parse("2026-08-12T08:00:00Z"), OffsetDateTime.parse("2026-08-12T17:00:00Z"),
                        "present")),
                2, 15, 1));

        mockMvc.perform(get("/attendance-records")
                .param("employee_id", ID.toString())
                .param("status", "present")
                .param("date_from", "2026-08-01")
                .param("date_to", "2026-08-12")
                .param("page", "2")
                .param("page_size", "15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(2))
                .andExpect(jsonPath("$.items[0].status").value("present"));

        ArgumentCaptor<AttendanceRecordFilter> captor = ArgumentCaptor.forClass(AttendanceRecordFilter.class);
        verify(attendanceService).records(captor.capture());
        org.junit.jupiter.api.Assertions.assertEquals(ID, captor.getValue().employeeId());
        org.junit.jupiter.api.Assertions.assertEquals(15, captor.getValue().pageSize());
    }

    @Test
    void recordSummary_shouldReturnServiceResponse() throws Exception {
        when(attendanceService.recordSummary(any()))
                .thenReturn(new AttendanceRecordSummaryDto(42, 32, 6, 4));

        mockMvc.perform(get("/attendance-records/summary").param("employee_id", ID.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords").value(42));
    }

    @Test
    void exportRecords_shouldReturnCsvHeaders() throws Exception {
        when(attendanceService.exportRecords(any())).thenReturn("id,employee_id,attendance_date,status\n");

        mockMvc.perform(get("/attendance-records/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/csv"))
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("attendance-records.csv")));
    }

    @Test
    void listCorrections_shouldBindOpenApiSnakeCaseParameters() throws Exception {
        when(attendanceService.listCorrections(any())).thenReturn(new PageResult<>(List.of(), 2, 10, 0));

        mockMvc.perform(get("/attendance-corrections")
                .param("employee_id", ID.toString())
                .param("date_from", "2026-08-01")
                .param("date_to", "2026-08-12")
                .param("page", "2")
                .param("page_size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(2));
    }

    @Test
    void listExceptions_shouldBindOpenApiSnakeCaseParameters() throws Exception {
        when(attendanceService.exceptions(any())).thenReturn(new PageResult<>(List.of(), 3, 15, 1));

        mockMvc.perform(get("/attendance-exceptions")
                .param("employee_id", ID.toString())
                .param("exception_type", "late_arrival")
                .param("date_from", "2026-08-01")
                .param("date_to", "2026-08-12")
                .param("page", "3")
                .param("page_size", "15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(3));

        ArgumentCaptor<AttendanceExceptionFilter> captor = ArgumentCaptor.forClass(AttendanceExceptionFilter.class);
        verify(attendanceService).exceptions(captor.capture());
        assertEquals(15, captor.getValue().pageSize());
        assertEquals("late_arrival", captor.getValue().exceptionType());
    }

    @Test
    void exceptionSummary_shouldReturnServiceResponse() throws Exception {
        when(attendanceService.exceptionSummary()).thenReturn(new AttendanceExceptionSummaryDto(12, 7, 5));

        mockMvc.perform(get("/attendance-exceptions/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalExceptions").value(12))
                .andExpect(jsonPath("$.resolvedExceptions").value(5));
    }

    @Test
    void canReachAttendanceCorrectionSummary() throws Exception {
        when(attendanceService.summary()).thenReturn(new AttendanceCorrectionSummary(24, 3, 2));

        mockMvc.perform(get("/attendance-corrections/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingReview").value(24));
    }
}
