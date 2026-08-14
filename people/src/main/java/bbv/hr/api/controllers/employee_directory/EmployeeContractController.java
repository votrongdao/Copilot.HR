package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.application.interfaces.employee_directory.ContractService;
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
 * REST Controller for managing Employee Contracts persisted in PostgreSQL.
 */
@Tag(name = "Employee Directory", description = "Labor contract history and salary registration APIs")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/contracts")
public class EmployeeContractController {

    private final ContractService contractService;

    public EmployeeContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Operation(
            summary = "Fetch labor contract history",
            description = "Returns all labor contracts for the specified employee, including contract type, salary, effective dates, and status."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract history retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ContractResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ContractResponse>> getEmployeeContracts(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId) {
        List<ContractResponse> responses = contractService.getEmployeeContracts(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Register labor contract and base salary",
            description = "Creates a labor contract record for the specified employee."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Contract created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContractResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payload or duplicate contract number",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Contract number already exists\"}"))
            )
    })
    @PostMapping
    public ResponseEntity<ContractResponse> createContract(
            @Parameter(description = "Employee ID", example = "EMP-0024", required = true) @PathVariable String employeeId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Labor contract creation payload",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateContractRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "contractNumber": "LC-2026-0024",
                                      "contractType": "FULL_TIME",
                                      "startDate": "2026-08-01",
                                      "endDate": "2027-07-31",
                                      "baseSalary": 2500,
                                      "status": "Active"
                                    }
                                    """)
                    )
            )
            @RequestBody CreateContractRequest request) {
        ContractResponse response = contractService.createContract(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
