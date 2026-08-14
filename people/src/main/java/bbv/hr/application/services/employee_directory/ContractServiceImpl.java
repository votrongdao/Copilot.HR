package bbv.hr.application.services.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;
import bbv.hr.application.interfaces.employee_directory.ContractService;
import bbv.hr.infrastructure.entities.employee_directory.Contract;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.repositories.employee_directory.ContractRepository;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Labor Contract operations querying PostgreSQL database.
 */
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               EmployeeRepository employeeRepository) {
        this.contractRepository = contractRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Fetch complete contract history from PostgreSQL for a given employee ID.
     */
    @Override
    public List<ContractResponse> getEmployeeContracts(String employeeId) {
        List<Contract> contracts = contractRepository.findByEmployeeId(employeeId);
        return contracts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Register a new labor contract and base salary record in PostgreSQL.
     */
    @Override
    public ContractResponse createContract(String employeeId, CreateContractRequest request) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        if (contractRepository.findByContractNumber(request.getContractNumber()).isPresent()) {
            throw new IllegalArgumentException("Contract number already exists: " + request.getContractNumber());
        }

        Contract contract = Contract.builder()
                .contractId("CNT-" + System.currentTimeMillis())
                .employee(employee)
                .contractNumber(request.getContractNumber())
                .contractType(request.getContractType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .baseSalary(request.getBaseSalary())
                .status(request.getStatus() != null ? request.getStatus() : "Active")
                .build();

        Contract saved = contractRepository.save(contract);
        return mapToResponse(saved);
    }

    private ContractResponse mapToResponse(Contract c) {
        return ContractResponse.builder()
                .contractId(c.getContractId())
                .contractNumber(c.getContractNumber())
                .contractType(c.getContractType())
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .baseSalary(c.getBaseSalary())
                .status(c.getStatus())
                .build();
    }
}
