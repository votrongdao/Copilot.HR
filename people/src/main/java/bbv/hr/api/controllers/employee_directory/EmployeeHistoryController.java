package bbv.hr.api.controllers.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Employee Career Audit Trail and History from PostgreSQL.
 */
@Tag(name = "Employee Directory", description = "Career History and Audit Log Timeline API")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/history")
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    public EmployeeHistoryController(EmployeeHistoryService employeeHistoryService) {
        this.employeeHistoryService = employeeHistoryService;
    }

    @Operation(summary = "Get career timeline and audit history", description = "Query career milestones and audit logs from PostgreSQL for an employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee history timeline retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getEmployeeHistory(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        List<Map<String, Object>> responses = employeeHistoryService.getEmployeeHistory(employeeId);
        return ResponseEntity.ok(responses);
    }
}
