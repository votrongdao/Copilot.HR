package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Contract;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Contract querying GetData component with in-memory caching.
 */
@Repository
public class ContractRepository {

    private final GetData getData;
    private final List<Contract> contracts = new ArrayList<>();

    public ContractRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all contract records lazily cached from JSON mock data.
     */
    public List<Contract> findAll() {
        if (contracts.isEmpty()) {
            List<Contract> loaded = getData.getEmployeeDirectoryEntities("contract", Contract.class);
            if (loaded != null) {
                contracts.addAll(loaded);
            }
        }
        return contracts;
    }

    /**
     * Find contract by contract ID.
     */
    public Contract findById(String contractId) {
        return findAll().stream()
                .filter(c -> c.getContractId() != null && c.getContractId().equalsIgnoreCase(contractId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find all contracts for a specific employee ID.
     */
    public List<Contract> findByEmployeeId(String employeeId) {
        return findAll().stream()
                .filter(c -> c.getEmployee() != null && c.getEmployee().getEmployeeId() != null
                        && c.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }

    /**
     * Find contract by unique contract number.
     */
    public Contract findByContractNumber(String contractNumber) {
        return findAll().stream()
                .filter(c -> c.getContractNumber() != null && c.getContractNumber().equalsIgnoreCase(contractNumber))
                .findFirst()
                .orElse(null);
    }
}
