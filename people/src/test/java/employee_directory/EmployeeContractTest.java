package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeContractController;
import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.application.interfaces.employee_directory.ContractService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeContractController.class)
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeContractTest extends EmployeeDirectoryApiTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContractService contractService;

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
}
