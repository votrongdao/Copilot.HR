package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

/**
 * REST Controller for managing Employee Records and Profiles with real PostgreSQL database persistence.
 */
@Tag(name = "Employee Directory", description = "APIs for searching, viewing, creating, updating, and offboarding employees")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Search and filter employee directory", description = "Query real PostgreSQL database records with search filters and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee list retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeSummaryResponse>> getEmployees(
            @Parameter(description = "Search keyword (email or employee ID)") @RequestParam(required = false) String search,
            @Parameter(description = "Filter by employment status (Active, Probation, Terminated)") @RequestParam(required = false) String status,
            @Parameter(description = "Page index (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size limit") @RequestParam(defaultValue = "10") int size
    ) {
        List<EmployeeSummaryResponse> responses = employeeService.getEmployees(search, status, page, size);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Create new employee record", description = "Persist a new employee and initial account into PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate email")
    })
    @PostMapping
    public ResponseEntity<EmployeeSummaryResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeSummaryResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get 360-degree employee profile by ID", description = "Retrieve full profile details from PostgreSQL database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee detail retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailResponse> getEmployeeById(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        EmployeeDetailResponse response = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update employee profile details", description = "Update contact, demographics, or profile details in PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailResponse> updateEmployee(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId,
            @RequestBody UpdateEmployeeProfileRequest request) {
        EmployeeDetailResponse response = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Offboard and deactivate employee", description = "Mark employee employment status as Terminated in PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee offboarded successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Export employee directory data", description = "Export real PostgreSQL employee records into CSV stream.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File exported successfully")
    })
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportEmployees(
            @Parameter(description = "Export format (csv or xlsx)") @RequestParam(defaultValue = "csv") String format) {
        InputStream stream = employeeService.exportEmployees(format);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees." + format);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(stream));
    }
}
