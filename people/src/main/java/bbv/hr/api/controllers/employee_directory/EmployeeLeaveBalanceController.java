package bbv.hr.api.controllers.employee_directory;

import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees/{id}/leave-balance")
@Tag(name = "Employee Directory - Leave Balance API", description = "APIs for retrieving leave entitlement quotas and real-time balances.")
public class EmployeeLeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public EmployeeLeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @GetMapping
    @Operation(summary = "TC-11: Retrieve Leave Quotas", description = "Retrieve annual leave quotas, carried over days, used days, and remaining balances per leave type.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leave balance quotas")
    public ResponseEntity<List<EmployeeLeaveBalance>> getLeaveBalance(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        List<EmployeeLeaveBalance> balances = leaveBalanceService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(balances);
    }
}
