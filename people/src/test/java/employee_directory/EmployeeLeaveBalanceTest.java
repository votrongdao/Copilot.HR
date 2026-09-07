package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeLeaveBalanceController;
import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeLeaveBalanceController.class)
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeLeaveBalanceTest extends EmployeeDirectoryApiTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeaveBalanceService leaveBalanceService;

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
}
