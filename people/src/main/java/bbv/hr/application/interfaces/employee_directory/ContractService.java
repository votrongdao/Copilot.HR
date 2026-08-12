package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Contract;

import java.util.List;

/**
 * Service interface for Labor Contract operations.
 */
public interface ContractService {

    /**
     * Fetch complete contract history for a given employee ID.
     */
    List<Contract> getEmployeeContracts(String employeeId);

    /**
     * Register a new labor contract and base salary record.
     */
    Contract createContract(String employeeId, Contract contract);
}
