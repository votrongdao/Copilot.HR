package bbv.hr.application.services.employee_directory;

import bbv.hr.application.interfaces.employee_directory.ContractService;
import bbv.hr.infrastructure.entities.employee_directory.Contract;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.repositories.employee_directory.ContractRepository;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for Labor Contract operations.
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
     * Fetch complete contract history for a given employee ID.
     */
    @Override
    public List<Contract> getEmployeeContracts(String employeeId) {
        return contractRepository.findByEmployeeId(employeeId);
    }

    /**
     * Register a new labor contract and base salary record for an employee.
     */
    @Override
    public Contract createContract(String employeeId, Contract contract) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        if (contractRepository.findByContractNumber(contract.getContractNumber()) != null) {
            throw new IllegalArgumentException("Contract number already exists: " + contract.getContractNumber());
        }
        contract.setEmployee(employee);
        return contract;
    }
}
