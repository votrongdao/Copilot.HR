package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Employee Verification Documents in PostgreSQL.
 */
@Tag(name = "Employee Directory", description = "Verification Documents and File Links API")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/documents")
public class EmployeeDocumentController {

    private final EmployeeDocumentService employeeDocumentService;

    public EmployeeDocumentController(EmployeeDocumentService employeeDocumentService) {
        this.employeeDocumentService = employeeDocumentService;
    }

    @Operation(summary = "Get employee verification documents", description = "Query uploaded document records from PostgreSQL for an employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documents retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeDocumentResponse>> getEmployeeDocuments(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        List<EmployeeDocumentResponse> responses = employeeDocumentService.getEmployeeDocuments(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Upload verification document metadata", description = "Save uploaded document reference record in PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payload")
    })
    @PostMapping
    public ResponseEntity<EmployeeDocumentResponse> uploadDocument(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId,
            @RequestBody UploadDocumentRequest request) {
        EmployeeDocumentResponse response = employeeDocumentService.uploadDocument(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
