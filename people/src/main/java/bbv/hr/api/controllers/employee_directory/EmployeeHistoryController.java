package bbv.hr.api.controllers.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees/{id}/history")
@Tag(name = "Employee Directory - Career History API", description = "APIs for fetching career audit trail logs and status timeline.")
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    public EmployeeHistoryController(EmployeeHistoryService employeeHistoryService) {
        this.employeeHistoryService = employeeHistoryService;
    }

    @GetMapping
    @Operation(summary = "TC-12: Fetch Career Audit Trail Logs", description = "Retrieve career audit trail events, status changes, and onboarding history for an employee.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved career audit trail history")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeHistory(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        List<Map<String, Object>> history = employeeHistoryService.getEmployeeHistory(employeeId);
        return ResponseEntity.ok(history);
    }
}
