package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeProfile;

import java.io.InputStream;
import java.util.List;

/**
 * Service interface for Employee Directory management operations.
 */
public interface EmployeeService {

    /**
     * Search and retrieve paginated list of employees.
     */
    List<Employee> getEmployees(String search, String status, int page, int size);

    /**
     * Register a new employee account and profile.
     */
    Employee createEmployee(Employee employee, EmployeeProfile profile);

    /**
     * Fetch 360-degree full profile details for a given employee ID.
     */
    EmployeeProfile getEmployeeById(String employeeId);

    /**
     * Update employee contact details and demographic information.
     */
    EmployeeProfile updateEmployee(String employeeId, EmployeeProfile profileUpdate);

    /**
     * Offboard employee and deactivate system account.
     */
    boolean deleteEmployee(String employeeId);

    /**
     * Export employee directory to CSV or XLSX file stream.
     */
    InputStream exportEmployees(String format);
}
