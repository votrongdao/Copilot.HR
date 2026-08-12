package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.responses.EmployeeLeaveBalanceResponse;
import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for retrieving Employee Leave Balances from PostgreSQL.
 */
@Tag(name = "Employee Directory", description = "Annual & Sick Leave Quota Balances API")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/leave-balances")
public class EmployeeLeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public EmployeeLeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @Operation(summary = "Get employee leave balance breakdown", description = "Retrieve leave quotas, used days, and remaining balances from PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave balances retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeLeaveBalanceResponse>> getLeaveBalance(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        List<EmployeeLeaveBalanceResponse> responses = leaveBalanceService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(responses);
    }
}
