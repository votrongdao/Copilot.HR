package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateContractRequest;
import bbv.hr.api.dtos.employee_directory.responses.ContractResponse;

import java.util.List;

/**
 * Service interface for Labor Contract operations.
 */
public interface ContractService {

    /**
     * Fetch complete contract history for a given employee ID.
     */
    List<ContractResponse> getEmployeeContracts(String employeeId);

    /**
     * Register a new labor contract and base salary record.
     */
    ContractResponse createContract(String employeeId, CreateContractRequest request);
}
