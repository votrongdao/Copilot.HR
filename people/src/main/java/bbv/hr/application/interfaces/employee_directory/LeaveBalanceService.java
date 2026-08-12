package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;

import java.util.List;

/**
 * Service interface for Leave Quota and Balance operations.
 */
public interface LeaveBalanceService {

    /**
     * Retrieve annual leave quotas and remaining balances for a given employee ID.
     */
    List<EmployeeLeaveBalance> getLeaveBalance(String employeeId);
}
