package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for EmployeeLeaveBalance entity querying GetData component with in-memory caching.
 */
@Repository
public class EmployeeLeaveBalanceRepository {

    private final GetData getData;
    private final List<EmployeeLeaveBalance> balances = new ArrayList<>();

    public EmployeeLeaveBalanceRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all employee leave balances lazily cached from JSON mock data.
     */
    public List<EmployeeLeaveBalance> findAll() {
        if (balances.isEmpty()) {
            List<EmployeeLeaveBalance> loaded = getData.getEmployeeDirectoryEntities("employee_leave_balance", EmployeeLeaveBalance.class);
            if (loaded != null) {
                balances.addAll(loaded);
            }
        }
        return balances;
    }

    /**
     * Find all leave balance quotas for a specific employee ID.
     */
    public List<EmployeeLeaveBalance> findByEmployeeId(String employeeId) {
        return findAll().stream()
                .filter(b -> b.getEmployee() != null && b.getEmployee().getEmployeeId() != null
                        && b.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }
}
