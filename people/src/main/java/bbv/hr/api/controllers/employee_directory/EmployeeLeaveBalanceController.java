package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.responses.EmployeeLeaveBalanceResponse;
import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Employee Directory", description = "Employee leave quota and balance APIs")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/leave-balances")
public class EmployeeLeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public EmployeeLeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @Operation(
            summary = "Retrieve leave quota balances",
            description = "Returns leave quota, used days, remaining days, and carried-over days grouped by leave type."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leave balances retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeLeaveBalanceResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EmployeeLeaveBalanceResponse>> getLeaveBalance(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        List<EmployeeLeaveBalanceResponse> responses = leaveBalanceService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(responses);
    }
}
