package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Employee Directory", description = "Employee profile, directory search, offboarding, and export APIs")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Search and paginate employees",
            description = "Returns employee directory rows filtered by optional keyword and employment status. Pagination is zero-based."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee list retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeSummaryResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<EmployeeSummaryResponse>> getEmployees(
            @Parameter(description = "Search keyword matching employee ID or email", example = "linh") @RequestParam(required = false) String search,
            @Parameter(description = "Employment status filter", example = "Active") @RequestParam(required = false) String status,
            @Parameter(description = "Page index, zero-based", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size
    ) {
        List<EmployeeSummaryResponse> responses = employeeService.getEmployees(search, status, page, size);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Register employee profile",
            description = "Creates a new employee account/profile. Corporate email must be unique."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeSummaryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or duplicate email",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Corporate email already exists: linh.nguyen@bbv.vn\"}"))
            )
    })
    @PostMapping
    public ResponseEntity<EmployeeSummaryResponse> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee registration payload",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateEmployeeRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "employeeId": "EMP-0024",
                                      "email": "linh.nguyen@bbv.vn",
                                      "departmentId": "DEP-ENG",
                                      "positionId": "POS-SE",
                                      "firstName": "Linh",
                                      "lastName": "Nguyen",
                                      "phone": "0900000000",
                                      "employmentStatus": "Active",
                                      "joinDate": "2026-08-01"
                                    }
                                    """)
                    )
            )
            @RequestBody CreateEmployeeRequest request) {
        EmployeeSummaryResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Fetch 360-degree employee profile",
            description = "Retrieves employee account, profile, education, certification, and assigned asset details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee detail retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Employee not found with ID: EMP-9999\"}"))
            )
    })
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailResponse> getEmployeeById(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        EmployeeDetailResponse response = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update demographics and contact information",
            description = "Updates mutable profile fields such as name, phone, avatar, date of birth, gender, and address."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee profile updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Employee not found with ID: EMP-9999\"}"))
            )
    })
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailResponse> updateEmployee(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile update payload. Only provided fields are updated.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateEmployeeProfileRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "firstName": "Linh",
                                      "lastName": "Tran",
                                      "phone": "0911111111",
                                      "avatarUrl": "https://cdn.example.com/avatars/emp-0024.png",
                                      "dateOfBirth": "1995-05-20",
                                      "gender": "Female",
                                      "address": "Ho Chi Minh City"
                                    }
                                    """)
                    )
            )
            @RequestBody UpdateEmployeeProfileRequest request) {
        EmployeeDetailResponse response = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Offboard and deactivate employee",
            description = "Marks the employee as terminated/deactivated. This endpoint does not hard-delete the employee record."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee offboarded successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Export employee directory",
            description = "Exports employee directory data. Current response content type is text/csv."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "File exported successfully",
                    content = @Content(mediaType = "text/csv", schema = @Schema(type = "string", format = "binary"))
            )
    })
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportEmployees(
            @Parameter(description = "Export format", example = "csv") @RequestParam(defaultValue = "csv") String format) {
        InputStream stream = employeeService.exportEmployees(format);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees." + format);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(stream));
    }
}
