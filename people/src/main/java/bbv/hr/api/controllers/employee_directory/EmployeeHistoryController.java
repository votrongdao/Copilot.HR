package bbv.hr.api.controllers.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
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
import java.util.Map;

/**
 * REST Controller for Employee Career Audit Trail and History from PostgreSQL.
 */
@Tag(name = "Employee Directory", description = "Employee career timeline and audit history APIs")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/history")
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    public EmployeeHistoryController(EmployeeHistoryService employeeHistoryService) {
        this.employeeHistoryService = employeeHistoryService;
    }

    @Operation(
            summary = "Fetch career promotion audit logs",
            description = "Returns career timeline entries such as promotions, department changes, and position changes."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee history timeline retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    implementation = Map.class,
                                    example = "{\"eventType\":\"PROMOTION\",\"fromPosition\":\"Junior Software Engineer\",\"toPosition\":\"Software Engineer\",\"effectiveDate\":\"2026-08-01\"}"
                            ))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getEmployeeHistory(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        List<Map<String, Object>> responses = employeeHistoryService.getEmployeeHistory(employeeId);
        return ResponseEntity.ok(responses);
    }
}
