package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeContractController;
import bbv.hr.api.controllers.employee_directory.EmployeeController;
import bbv.hr.api.controllers.employee_directory.EmployeeDocumentController;
import bbv.hr.api.controllers.employee_directory.EmployeeHistoryController;
import bbv.hr.api.controllers.employee_directory.EmployeeLeaveBalanceController;
import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeLeaveBalanceResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeProfileResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import bbv.hr.application.interfaces.employee_directory.ContractService;
import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
import bbv.hr.application.interfaces.employee_directory.EmployeeService;
import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        EmployeeController.class,
        EmployeeContractController.class,
        EmployeeDocumentController.class,
        EmployeeLeaveBalanceController.class,
        EmployeeHistoryController.class
})
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeDirectoryApiTest {

    private static final String EMPLOYEE_ID = "EMP-0024";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private ContractService contractService;

    @MockitoBean
    private EmployeeDocumentService employeeDocumentService;

    @MockitoBean
    private LeaveBalanceService leaveBalanceService;

    @MockitoBean
    private EmployeeHistoryService employeeHistoryService;

    @Test
    @DisplayName("TC-01 [P0] Search & Paginated Employees List")
    void shouldSearchAndPaginateEmployeesList() throws Exception {
        when(employeeService.getEmployees("linh", "Active", 1, 5))
                .thenReturn(List.of(employeeSummary()));

        mockMvc.perform(get("/api/v1/employees")
                        .param("search", "linh")
                        .param("status", "Active")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$[0].email").value("linh.nguyen@bbv.vn"))
                .andExpect(jsonPath("$[0].employmentStatus").value("Active"));

        verify(employeeService).getEmployees("linh", "Active", 1, 5);
    }

    @Test
    @DisplayName("TC-02 [P0] Register Employee Profile")
    void shouldRegisterEmployeeProfile() throws Exception {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeId(EMPLOYEE_ID)
                .email("linh.nguyen@bbv.vn")
                .departmentId("DEP-ENG")
                .positionId("POS-SE")
                .firstName("Linh")
                .lastName("Nguyen")
                .phone("0900000000")
                .employmentStatus("Active")
                .joinDate(LocalDate.of(2026, 8, 1))
                .build();

        when(employeeService.createEmployee(any(CreateEmployeeRequest.class)))
                .thenReturn(employeeSummary());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.fullName").value("Linh Nguyen"));

        verify(employeeService).createEmployee(any(CreateEmployeeRequest.class));
    }

    @Test
    @DisplayName("TC-03 [P0] Validate Duplicate Email Error")
    void shouldValidateDuplicateEmailError() throws Exception {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeId("EMP-0099")
                .email("linh.nguyen@bbv.vn")
                .firstName("Duplicate")
                .lastName("Email")
                .build();

        when(employeeService.createEmployee(any(CreateEmployeeRequest.class)))
                .thenThrow(new IllegalArgumentException("Corporate email already exists: linh.nguyen@bbv.vn"));

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(employeeService).createEmployee(any(CreateEmployeeRequest.class));
    }

    @Test
    @DisplayName("TC-04 [P0] Fetch 360-Degree Profile Details")
    void shouldFetchEmployeeProfileDetails() throws Exception {
        when(employeeService.getEmployeeById(EMPLOYEE_ID))
                .thenReturn(employeeDetail());

        mockMvc.perform(get("/api/v1/employees/{employeeId}", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.profile.firstName").value("Linh"))
                .andExpect(jsonPath("$.profile.lastName").value("Nguyen"));

        verify(employeeService).getEmployeeById(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-05 [P1] Update Demographics & Contact Info")
    void shouldUpdateDemographicsAndContactInfo() throws Exception {
        UpdateEmployeeProfileRequest request = UpdateEmployeeProfileRequest.builder()
                .firstName("Linh")
                .lastName("Tran")
                .phone("0911111111")
                .dateOfBirth(LocalDate.of(1995, 5, 20))
                .gender("Female")
                .address("Ho Chi Minh City")
                .build();

        EmployeeDetailResponse response = employeeDetail();
        response.getProfile().setLastName("Tran");
        response.getProfile().setPhone("0911111111");
        response.getProfile().setAddress("Ho Chi Minh City");

        when(employeeService.updateEmployee(eq(EMPLOYEE_ID), any(UpdateEmployeeProfileRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/employees/{employeeId}", EMPLOYEE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.lastName").value("Tran"))
                .andExpect(jsonPath("$.profile.phone").value("0911111111"))
                .andExpect(jsonPath("$.profile.address").value("Ho Chi Minh City"));

        verify(employeeService).updateEmployee(eq(EMPLOYEE_ID), any(UpdateEmployeeProfileRequest.class));
    }

    @Test
    @DisplayName("TC-06 [P1] Offboard & Deactivate Employee Profile")
    void shouldOffboardAndDeactivateEmployeeProfile() throws Exception {
        when(employeeService.deleteEmployee(EMPLOYEE_ID)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/employees/{employeeId}", EMPLOYEE_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(employeeService).deleteEmployee(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-07 [P1] Fetch Labor Contract History")
    void shouldFetchLaborContractHistory() throws Exception {
        when(contractService.getEmployeeContracts(EMPLOYEE_ID))
                .thenReturn(List.of(contractResponse()));

        mockMvc.perform(get("/api/v1/employees/{employeeId}/contracts", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contractNumber").value("LC-2026-0024"))
                .andExpect(jsonPath("$[0].baseSalary").value(2500));

        verify(contractService).getEmployeeContracts(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-08 [P1] Register Labor Contract & Salary")
    void shouldRegisterLaborContractAndSalary() throws Exception {
        CreateContractRequest request = CreateContractRequest.builder()
                .contractNumber("LC-2026-0024")
                .contractType("FULL_TIME")
                .startDate(LocalDate.of(2026, 8, 1))
                .endDate(LocalDate.of(2027, 7, 31))
                .baseSalary(BigDecimal.valueOf(2500))
                .status("Active")
                .build();

        when(contractService.createContract(eq(EMPLOYEE_ID), any(CreateContractRequest.class)))
                .thenReturn(contractResponse());

        mockMvc.perform(post("/api/v1/employees/{employeeId}/contracts", EMPLOYEE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value("LC-2026-0024"))
                .andExpect(jsonPath("$.status").value("Active"));

        verify(contractService).createContract(eq(EMPLOYEE_ID), any(CreateContractRequest.class));
    }

    @Test
    @DisplayName("TC-09 [P2] Fetch Verification Documents List")
    void shouldFetchVerificationDocumentsList() throws Exception {
        when(employeeDocumentService.getEmployeeDocuments(EMPLOYEE_ID))
                .thenReturn(List.of(documentResponse()));

        mockMvc.perform(get("/api/v1/employees/{employeeId}/documents", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentType").value("ID_CARD"))
                .andExpect(jsonPath("$[0].documentName").value("National ID"));

        verify(employeeDocumentService).getEmployeeDocuments(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-10 [P2] Upload Document File & Validation")
    void shouldUploadDocumentFileAndValidation() throws Exception {
        UploadDocumentRequest request = UploadDocumentRequest.builder()
                .documentType("ID_CARD")
                .documentName("National ID")
                .documentUrl("https://files.example.com/emp-0024/id-card.pdf")
                .build();

        when(employeeDocumentService.uploadDocument(eq(EMPLOYEE_ID), any(UploadDocumentRequest.class)))
                .thenReturn(documentResponse());

        mockMvc.perform(post("/api/v1/employees/{employeeId}/documents", EMPLOYEE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentId").value("DOC-0024"))
                .andExpect(jsonPath("$.documentUrl").value("https://files.example.com/emp-0024/id-card.pdf"));

        verify(employeeDocumentService).uploadDocument(eq(EMPLOYEE_ID), any(UploadDocumentRequest.class));
    }

    @Test
    @DisplayName("TC-11 [P1] Retrieve Leave Quota Balances")
    void shouldRetrieveLeaveQuotaBalances() throws Exception {
        when(leaveBalanceService.getLeaveBalance(EMPLOYEE_ID))
                .thenReturn(List.of(leaveBalanceResponse()));

        mockMvc.perform(get("/api/v1/employees/{employeeId}/leave-balances", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].leaveTypeCode").value("ANNUAL"))
                .andExpect(jsonPath("$[0].remainingDays").value(8));

        verify(leaveBalanceService).getLeaveBalance(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-12 [P2] Fetch Career Promotion Audit Logs")
    void shouldFetchCareerPromotionAuditLogs() throws Exception {
        when(employeeHistoryService.getEmployeeHistory(EMPLOYEE_ID))
                .thenReturn(List.of(Map.of(
                        "eventType", "PROMOTION",
                        "fromPosition", "Junior Software Engineer",
                        "toPosition", "Software Engineer",
                        "effectiveDate", "2026-08-01"
                )));

        mockMvc.perform(get("/api/v1/employees/{employeeId}/history", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventType").value("PROMOTION"))
                .andExpect(jsonPath("$[0].toPosition").value("Software Engineer"));

        verify(employeeHistoryService).getEmployeeHistory(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-13 [P2] Export Employee Directory to CSV/XLSX")
    void shouldExportEmployeeDirectoryToCsvOrXlsx() throws Exception {
        String csv = "EmployeeID,Email,Status,DepartmentID\nEMP-0024,linh.nguyen@bbv.vn,Active,DEP-ENG\n";
        when(employeeService.exportEmployees("csv"))
                .thenReturn(new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8)));

        mockMvc.perform(get("/api/v1/employees/export").param("format", "csv"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", containsString("employees.csv")))
                .andExpect(content().contentTypeCompatibleWith("text/csv"))
                .andExpect(content().string(csv));

        verify(employeeService).exportEmployees("csv");
    }

    private EmployeeSummaryResponse employeeSummary() {
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

    private EmployeeDetailResponse employeeDetail() {
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

    private ContractResponse contractResponse() {
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

    private EmployeeDocumentResponse documentResponse() {
        return EmployeeDocumentResponse.builder()
                .documentId("DOC-0024")
                .documentType("ID_CARD")
                .documentName("National ID")
                .documentUrl("https://files.example.com/emp-0024/id-card.pdf")
                .uploadedAt(LocalDateTime.of(2026, 8, 1, 9, 0))
                .build();
    }

    private EmployeeLeaveBalanceResponse leaveBalanceResponse() {
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
