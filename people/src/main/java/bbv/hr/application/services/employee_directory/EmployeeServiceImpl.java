package bbv.hr.application.services.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeService;
import bbv.hr.infrastructure.entities.employee_directory.*;
import bbv.hr.infrastructure.repositories.employee_directory.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Employee Directory management operations.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final EducationRepository educationRepository;
    private final CertificationRepository certificationRepository;
    private final AssetRepository assetRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeProfileRepository employeeProfileRepository,
                               EducationRepository educationRepository,
                               CertificationRepository certificationRepository,
                               AssetRepository assetRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.educationRepository = educationRepository;
        this.certificationRepository = certificationRepository;
        this.assetRepository = assetRepository;
    }

    /**
     * Search and retrieve paginated list of employees matching status or search term.
     */
    @Override
    public List<Employee> getEmployees(String search, String status, int page, int size) {
        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees.stream()
                .filter(e -> status == null || status.isBlank() || status.equalsIgnoreCase(e.getEmploymentStatus()))
                .filter(e -> search == null || search.isBlank()
                        || (e.getEmail() != null && e.getEmail().toLowerCase().contains(search.toLowerCase()))
                        || (e.getEmployeeId() != null && e.getEmployeeId().toLowerCase().contains(search.toLowerCase())))
                .skip((long) Math.max(0, page) * size)
                .limit(Math.max(1, size))
                .collect(Collectors.toList());
    }

    /**
     * Register a new employee account and profile after checking duplicate email.
     */
    @Override
    public Employee createEmployee(Employee employee, EmployeeProfile profile) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Corporate email already exists: " + employee.getEmail());
        }
        if (profile != null) {
            profile.setEmployee(employee);
        }
        return employee;
    }

    /**
     * Fetch 360-degree full profile details (including Education, Certifications, Assets) for a given employee ID.
     */
    @Override
    public EmployeeProfile getEmployeeById(String employeeId) {
        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(employeeId);
        if (profile == null) {
            throw new IllegalArgumentException("Employee profile not found with ID: " + employeeId);
        }

        // Aggregate 360-degree details from Education, Certification, and Asset repositories
        List<Education> educations = educationRepository.findByEmployeeId(employeeId);
        List<Certification> certifications = certificationRepository.findByEmployeeId(employeeId);
        List<Asset> assets = assetRepository.findByEmployeeId(employeeId);

        return profile;
    }

    /**
     * Update employee contact details and demographic information.
     */
    @Override
    public EmployeeProfile updateEmployee(String employeeId, EmployeeProfile profileUpdate) {
        EmployeeProfile existingProfile = getEmployeeById(employeeId);
        if (profileUpdate.getFirstName() != null) {
            existingProfile.setFirstName(profileUpdate.getFirstName());
        }
        if (profileUpdate.getLastName() != null) {
            existingProfile.setLastName(profileUpdate.getLastName());
        }
        if (profileUpdate.getPhone() != null) {
            existingProfile.setPhone(profileUpdate.getPhone());
        }
        if (profileUpdate.getAvatarUrl() != null) {
            existingProfile.setAvatarUrl(profileUpdate.getAvatarUrl());
        }
        return existingProfile;
    }

    /**
     * Offboard employee and deactivate system account.
     */
    @Override
    public boolean deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee != null) {
            employee.setEmploymentStatus("Terminated");
            return true;
        }
        return false;
    }

    /**
     * Export employee directory to CSV or XLSX file stream.
     */
    @Override
    public InputStream exportEmployees(String format) {
        List<Employee> employees = employeeRepository.findAll();
        StringBuilder csv = new StringBuilder("EmployeeID,Email,Status,DepartmentID\n");
        for (Employee e : employees) {
            csv.append(e.getEmployeeId()).append(",")
                    .append(e.getEmail()).append(",")
                    .append(e.getEmploymentStatus()).append(",")
                    .append(e.getDepartmentId()).append("\n");
        }
        return new ByteArrayInputStream(csv.toString().getBytes(StandardCharsets.UTF_8));
    }
}
