package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Employee entity querying GetData component.
 */
@Repository
public class EmployeeRepository {

    private final GetData getData;
    private List <Employee> employees = new ArrayList<>();

    public EmployeeRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all employee records from JSON mock data.
     */
    public List<Employee> findAll() {
        return employees = getData.getEmployeeDirectoryEntities("employee", Employee.class);
    }

    /**
     * Find employee record by ID from JSON mock data.
     */
    public Employee findById(String employeeId) {
        return employees.stream()
                .filter(e -> e.getEmployeeId() != null && e.getEmployeeId().equalsIgnoreCase(employeeId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find employee record by corporate email.
     */
    public Employee findByEmail(String email) {
        return employees.stream()
                .filter(e -> e.getEmail() != null && e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if corporate email already exists in JSON data.
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    /**
     * Filter employees by employment status.
     */
    public List<Employee> findByEmploymentStatus(String status) {
        return employees.stream()
                .filter(e -> e.getEmploymentStatus() != null && e.getEmploymentStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
