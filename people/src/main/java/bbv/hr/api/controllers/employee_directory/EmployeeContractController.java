package bbv.hr.api.controllers.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.application.interfaces.employee_directory.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees/{id}/contracts")
@Tag(name = "Employee Directory - Contract API", description = "APIs for managing labor contracts and salary history.")
public class EmployeeContractController {

    private final ContractService contractService;

    public EmployeeContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    @Operation(summary = "TC-07: Fetch Contract History", description = "Retrieve all labor contract records and salary terms for a given employee ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved contract history")
    public ResponseEntity<List<ContractResponse>> getEmployeeContracts(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId) {
        List<ContractResponse> contracts = contractService.getEmployeeContracts(employeeId);
        return ResponseEntity.ok(contracts);
    }

    @PostMapping
    @Operation(summary = "TC-08: Register Labor Contract & Salary", description = "Register a new labor contract and base compensation record for an employee.")
    @ApiResponse(responseCode = "201", description = "Labor contract registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or duplicate contract number")
    public ResponseEntity<?> createContract(
            @Parameter(description = "Employee ID (e.g. EMP-0024)") @PathVariable("id") String employeeId,
            @RequestBody CreateContractRequest request) {
        try {
            ContractResponse created = contractService.createContract(employeeId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
