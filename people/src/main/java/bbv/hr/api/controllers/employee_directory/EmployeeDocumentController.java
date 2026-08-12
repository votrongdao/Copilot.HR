package bbv.hr.api.controllers.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees/{id}/documents")
@Tag(name = "Employee Directory - Document API", description = "APIs for viewing and uploading personal verification documents.")
public class EmployeeDocumentController {

    private final EmployeeDocumentService employeeDocumentService;

    public EmployeeDocumentController(EmployeeDocumentService employeeDocumentService) {
        this.employeeDocumentService = employeeDocumentService;
    }

    @GetMapping
    @Operation(summary = "TC-09: Fetch Verification Documents", description = "Retrieve list of scanned personal identity documents and files for an employee.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved verification document list")
    public ResponseEntity<List<EmployeeDocument>> getEmployeeDocuments(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        List<EmployeeDocument> documents = employeeDocumentService.getEmployeeDocuments(employeeId);
        return ResponseEntity.ok(documents);
    }

    @PostMapping
    @Operation(summary = "TC-10: Upload Document File & Format Check", description = "Upload a new scanned identity or verification document for an employee.")
    @ApiResponse(responseCode = "201", description = "Document uploaded successfully")
    @ApiResponse(responseCode = "400", description = "Invalid employee or document metadata")
    public ResponseEntity<?> uploadDocument(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId,
            @RequestBody EmployeeDocument document) {
        try {
            EmployeeDocument uploaded = employeeDocumentService.uploadDocument(employeeId, document);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploaded);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
