package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Employee querying GetData component with in-memory caching.
 */
@Repository
public class EmployeeRepository {

    private final GetData getData;
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all employee records lazily cached from JSON mock data.
     */
    public List<Employee> findAll() {
        if (employees.isEmpty()) {
            List<Employee> loaded = getData.getEmployeeDirectoryEntities("employee", Employee.class);
            if (loaded != null) {
                employees.addAll(loaded);
            }
        }
        return employees;
    }

    /**
     * Find employee record by ID from cached JSON mock data.
     */
    public Employee findById(String employeeId) {
        return findAll().stream()
                .filter(e -> e.getEmployeeId() != null && e.getEmployeeId().equalsIgnoreCase(employeeId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find employee record by corporate email.
     */
    public Employee findByEmail(String email) {
        return findAll().stream()
                .filter(e -> e.getEmail() != null && e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if corporate email already exists in cached JSON data.
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    /**
     * Filter employees by employment status.
     */
    public List<Employee> findByEmploymentStatus(String status) {
        return findAll().stream()
                .filter(e -> e.getEmploymentStatus() != null && e.getEmploymentStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
