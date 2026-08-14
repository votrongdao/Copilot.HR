package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Employee Directory", description = "Employee verification document metadata APIs")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/documents")
public class EmployeeDocumentController {

    private final EmployeeDocumentService employeeDocumentService;

    public EmployeeDocumentController(EmployeeDocumentService employeeDocumentService) {
        this.employeeDocumentService = employeeDocumentService;
    }

    @Operation(
            summary = "Fetch verification documents",
            description = "Returns uploaded verification document metadata for the specified employee."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Documents retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeDocumentResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EmployeeDocumentResponse>> getEmployeeDocuments(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        List<EmployeeDocumentResponse> responses = employeeDocumentService.getEmployeeDocuments(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Upload verification document metadata",
            description = "Saves document metadata and file URL for an employee. File binary upload is handled externally; this endpoint stores the reference."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Document record created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDocumentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payload",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Invalid document payload\"}"))
            )
    })
    @PostMapping
    public ResponseEntity<EmployeeDocumentResponse> uploadDocument(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification document metadata payload",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UploadDocumentRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "documentType": "ID_CARD",
                                      "documentName": "National ID",
                                      "documentUrl": "https://files.example.com/emp-0024/id-card.pdf"
                                    }
                                    """)
                    )
            )
            @RequestBody UploadDocumentRequest request) {
        EmployeeDocumentResponse response = employeeDocumentService.uploadDocument(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
