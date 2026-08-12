package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.application.interfaces.employee_directory.ContractService;
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
@Tag(name = "Employee Directory", description = "Labor Contracts and Base Salary API")
@RestController
@RequestMapping("/api/v1/employees/{employeeId}/contracts")
public class EmployeeContractController {

    private final ContractService contractService;

    public EmployeeContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Operation(summary = "Get contract history for employee", description = "Fetch labor contract records from PostgreSQL for a given employee ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping
    public ResponseEntity<List<ContractResponse>> getEmployeeContracts(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId) {
        List<ContractResponse> responses = contractService.getEmployeeContracts(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Create new labor contract", description = "Persist a new labor contract and base salary record into PostgreSQL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payload or duplicate contract number")
    })
    @PostMapping
    public ResponseEntity<ContractResponse> createContract(
            @Parameter(description = "Employee ID (e.g., EMP-0024)") @PathVariable String employeeId,
            @RequestBody CreateContractRequest request) {
        ContractResponse response = contractService.createContract(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
