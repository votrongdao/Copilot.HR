package employee_directory;

import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeLeaveBalanceResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeProfileResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

abstract class EmployeeDirectoryApiTestSupport {

    static final String EMPLOYEE_ID = "EMP-0024";

    final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    EmployeeSummaryResponse employeeSummary() {
        return EmployeeSummaryResponse.builder()
                .employeeId(EMPLOYEE_ID)
                .email("linh.nguyen@bbv.vn")
                .departmentId("DEP-ENG")
                .positionId("POS-SE")
                .employmentStatus("Active")
                .joinDate(LocalDate.of(2026, 8, 1))
                .fullName("Linh Nguyen")
                .avatarUrl("https://cdn.example.com/avatars/emp-0024.png")
                .build();
    }

    EmployeeDetailResponse employeeDetail() {
        return EmployeeDetailResponse.builder()
                .employeeId(EMPLOYEE_ID)
                .email("linh.nguyen@bbv.vn")
                .departmentId("DEP-ENG")
                .positionId("POS-SE")
                .employmentStatus("Active")
                .joinDate(LocalDate.of(2026, 8, 1))
                .profile(EmployeeProfileResponse.builder()
                        .profileId("PROF-0024")
                        .firstName("Linh")
                        .lastName("Nguyen")
                        .phone("0900000000")
                        .avatarUrl("https://cdn.example.com/avatars/emp-0024.png")
                        .dateOfBirth(LocalDate.of(1995, 5, 20))
                        .gender("Female")
                        .address("Da Nang")
                        .build())
                .educations(List.of())
                .certifications(List.of())
                .assets(List.of())
                .build();
    }

    ContractResponse contractResponse() {
        return ContractResponse.builder()
                .contractId("CON-0024")
                .contractNumber("LC-2026-0024")
                .contractType("FULL_TIME")
                .startDate(LocalDate.of(2026, 8, 1))
                .endDate(LocalDate.of(2027, 7, 31))
                .baseSalary(BigDecimal.valueOf(2500))
                .status("Active")
                .build();
    }

    EmployeeDocumentResponse documentResponse() {
        return EmployeeDocumentResponse.builder()
                .documentId("DOC-0024")
                .documentType("ID_CARD")
                .documentName("National ID")
                .documentUrl("https://files.example.com/emp-0024/id-card.pdf")
                .uploadedAt(LocalDateTime.of(2026, 8, 1, 9, 0))
                .build();
    }

    EmployeeLeaveBalanceResponse leaveBalanceResponse() {
        return EmployeeLeaveBalanceResponse.builder()
                .balanceId("BAL-0024")
                .leaveTypeCode("ANNUAL")
                .leaveTypeName("Annual Paid Leave")
                .totalQuota(BigDecimal.valueOf(12))
                .usedDays(BigDecimal.valueOf(4))
                .remainingDays(BigDecimal.valueOf(8))
                .carriedOverDays(BigDecimal.ZERO)
                .build();
    }
}
