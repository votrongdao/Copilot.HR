package bbv.hr.application.interfaces.employee_directory;

import java.util.List;
import java.util.Map;

/**
 * Service interface for Career Audit Trail & History operations.
 */
public interface EmployeeHistoryService {

    /**
     * Fetch career audit trail logs and status timeline for a given employee ID.
     */
    List<Map<String, Object>> getEmployeeHistory(String employeeId);
}
