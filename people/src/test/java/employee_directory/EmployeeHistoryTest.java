package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeHistoryController;
import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeHistoryController.class)
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeHistoryTest extends EmployeeDirectoryApiTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeHistoryService employeeHistoryService;

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
}
