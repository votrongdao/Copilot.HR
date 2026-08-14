package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDetailResponse;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;

import java.io.InputStream;
import java.util.List;

/**
 * Service interface for Employee Directory management operations.
 */
public interface EmployeeService {

    /**
     * Search and retrieve paginated list of employees.
     */
    List<EmployeeSummaryResponse> getEmployees(String search, String status, int page, int size);

    /**
     * Register a new employee account and profile.
     */
    EmployeeSummaryResponse createEmployee(CreateEmployeeRequest request);

    /**
     * Fetch 360-degree full profile details for a given employee ID.
     */
    EmployeeDetailResponse getEmployeeById(String employeeId);

    /**
     * Update employee contact details and demographic information.
     */
    EmployeeDetailResponse updateEmployee(String employeeId, UpdateEmployeeProfileRequest request);

    /**
     * Offboard employee and deactivate system account.
     */
    boolean deleteEmployee(String employeeId);

    /**
     * Export employee directory to CSV or XLSX file stream.
     */
    InputStream exportEmployees(String format);
}
