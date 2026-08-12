package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Directory - Core API", description = "APIs for searching, creating, viewing 360 profile, updating, deleting, and exporting employees.")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "TC-01: Search & Paginated Employees List", description = "Retrieve a paginated list of employees filtered by status and search query.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated employee list")
    public ResponseEntity<List<EmployeeSummaryResponse>> getEmployees(
            @Parameter(description = "Keyword search for email or employee ID") @RequestParam(required = false) String search,
            @Parameter(description = "Employment status (e.g. Active, Probation, Terminated)") @RequestParam(required = false) String status,
            @Parameter(description = "Page index (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size limit") @RequestParam(defaultValue = "10") int size) {
        List<EmployeeSummaryResponse> employees = employeeService.getEmployees(search, status, page, size);
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    @Operation(summary = "TC-02 / TC-03: Register New Employee Profile", description = "Create a new employee account and profile. Throws 409 Conflict if corporate email already exists.")
    @ApiResponse(responseCode = "201", description = "Employee profile created successfully")
    @ApiResponse(responseCode = "409", description = "Corporate email already exists")
    public ResponseEntity<?> createEmployee(
            @RequestBody CreateEmployeeRequest request) {
        try {
            EmployeeSummaryResponse created = employeeService.createEmployee(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "TC-04: Fetch 360-Degree Profile Details", description = "Retrieve complete 360-degree profile details including Education, Certifications, and Assets.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved 360-degree profile details")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public ResponseEntity<?> getEmployeeById(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        try {
            EmployeeDetailResponse profile = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "TC-05: Update Contact & Demographics", description = "Update employee demographic information, contact phone, and avatar URL.")
    @ApiResponse(responseCode = "200", description = "Employee profile updated successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public ResponseEntity<?> updateEmployee(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId,
            @RequestBody UpdateEmployeeProfileRequest request) {
        try {
            EmployeeDetailResponse updated = employeeService.updateEmployee(employeeId, request);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "TC-06: Offboard & Deactivate Account", description = "Deactivate employee account and mark status as Terminated.")
    @ApiResponse(responseCode = "200", description = "Account deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public ResponseEntity<?> deleteEmployee(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        boolean success = employeeService.deleteEmployee(employeeId);
        if (success) {
            return ResponseEntity.ok("Employee account " + employeeId + " deactivated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found: " + employeeId);
    }

    @PostMapping("/export")
    @Operation(summary = "TC-13: Export Directory to CSV/XLSX", description = "Export employee directory into a downloadable CSV file stream.")
    @ApiResponse(responseCode = "200", description = "File stream generated successfully")
    public ResponseEntity<InputStreamResource> exportEmployees(
            @Parameter(description = "Export file format (csv or xlsx)") @RequestParam(defaultValue = "csv") String format) {
        InputStream stream = employeeService.exportEmployees(format);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employees_export." + format);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}
