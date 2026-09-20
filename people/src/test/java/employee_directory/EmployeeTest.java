package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeController;
import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeService;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

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

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeTest extends EmployeeDirectoryApiTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

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
}
