package bbv.hr.application.services.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeHistoryService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service implementation for Career Audit Trail & History operations.
 */
@Service
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

    private final EmployeeRepository employeeRepository;

    public EmployeeHistoryServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Fetch career audit trail logs and status timeline for a given employee ID.
     */
    @Override
    public List<Map<String, Object>> getEmployeeHistory(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        List<Map<String, Object>> history = new ArrayList<>();

        if (employee != null) {
            Map<String, Object> joinLog = new HashMap<>();
            joinLog.put("event", "EMPLOYEE_JOINED");
            joinLog.put("timestamp", employee.getJoinDate() != null ? employee.getJoinDate().atStartOfDay() : LocalDateTime.now());
            joinLog.put("details", "Employee onboarded into " + employee.getDepartmentId());
            history.add(joinLog);

            Map<String, Object> statusLog = new HashMap<>();
            statusLog.put("event", "STATUS_UPDATED");
            statusLog.put("timestamp", LocalDateTime.now());
            statusLog.put("details", "Current status: " + employee.getEmploymentStatus());
            history.add(statusLog);
        }

        return history;
    }
}
