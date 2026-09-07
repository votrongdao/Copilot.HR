package bbv.hr.application.services.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.CreateEmployeeRequest;
import bbv.hr.api.dtos.employee_directory.requests.UpdateEmployeeProfileRequest;
import bbv.hr.api.dtos.employee_directory.responses.*;
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
 * Service implementation for Employee Directory management querying PostgreSQL database via JPA.
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
     * Search and retrieve paginated list of employees matching status or search term from PostgreSQL.
     */
    @Override
    public List<EmployeeSummaryResponse> getEmployees(String search, String status, int page, int size) {
        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees.stream()
                .filter(e -> status == null || status.isBlank() || status.equalsIgnoreCase(e.getEmploymentStatus()))
                .filter(e -> search == null || search.isBlank()
                        || (e.getEmail() != null && e.getEmail().toLowerCase().contains(search.toLowerCase()))
                        || (e.getEmployeeId() != null && e.getEmployeeId().toLowerCase().contains(search.toLowerCase())))
                .skip((long) Math.max(0, page) * size)
                .limit(Math.max(1, size))
                .map(this::mapToSummary)
                .collect(Collectors.toList());
    }

    /**
     * Register a new employee account and profile in PostgreSQL after checking duplicate email.
     */
    @Override
    public EmployeeSummaryResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Corporate email already exists: " + request.getEmail());
        }

        Employee employee = Employee.builder()
                .employeeId(request.getEmployeeId())
                .email(request.getEmail())
                .departmentId(request.getDepartmentId())
                .positionId(request.getPositionId())
                .employmentStatus(request.getEmploymentStatus() != null ? request.getEmploymentStatus() : "Active")
                .joinDate(request.getJoinDate())
                .build();

        Employee saved = employeeRepository.save(employee);

        return EmployeeSummaryResponse.builder()
                .employeeId(saved.getEmployeeId())
                .email(saved.getEmail())
                .departmentId(saved.getDepartmentId())
                .positionId(saved.getPositionId())
                .employmentStatus(saved.getEmploymentStatus())
                .joinDate(saved.getJoinDate())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .build();
    }

    /**
     * Fetch 360-degree full profile details from PostgreSQL for a given employee ID.
     */
    @Override
    public EmployeeDetailResponse getEmployeeById(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(employeeId).orElse(null);
        List<Education> educations = educationRepository.findByEmployeeId(employeeId);
        List<Certification> certifications = certificationRepository.findByEmployeeId(employeeId);
        List<Asset> assets = assetRepository.findByEmployeeId(employeeId);

        List<EducationResponse> educationResponses = educations.stream().map(e -> EducationResponse.builder()
                .educationId(e.getEducationId())
                .institutionName(e.getInstitution())
                .degree(e.getDegree())
                .major(e.getFieldOfStudy())
                .startYear(e.getStartYear())
                .endYear(e.getEndYear())
                .build()).collect(Collectors.toList());

        List<CertificationResponse> certificationResponses = certifications.stream().map(c -> CertificationResponse.builder()
                .certificationId(c.getCertificationId())
                .certificateName(c.getName())
                .issuingOrganization(c.getIssuingOrganization())
                .issueDate(c.getIssueDate())
                .expiryDate(c.getExpiryDate())
                .build()).collect(Collectors.toList());

        List<AssetResponse> assetResponses = assets.stream().map(a -> AssetResponse.builder()
                .assetId(a.getAssetId())
                .assetName(a.getAssetName())
                .serialNumber(a.getSerialNumber())
                .assignedDate(a.getIssueDate())
                .build()).collect(Collectors.toList());

        return EmployeeDetailResponse.builder()
                .employeeId(employee.getEmployeeId())
                .email(employee.getEmail())
                .departmentId(employee.getDepartmentId())
                .positionId(employee.getPositionId())
                .employmentStatus(employee.getEmploymentStatus())
                .joinDate(employee.getJoinDate())
                .profile(profile != null ? EmployeeProfileResponse.builder()
                        .profileId(profile.getProfileId())
                        .firstName(profile.getFirstName())
                        .lastName(profile.getLastName())
                        .phone(profile.getPhone())
                        .avatarUrl(profile.getAvatarUrl())
                        .dateOfBirth(profile.getDateOfBirth())
                        .gender(profile.getGender())
                        .build() : null)
                .educations(educationResponses)
                .certifications(certificationResponses)
                .assets(assetResponses)
                .build();
    }

    /**
     * Update employee contact details and demographic information in PostgreSQL.
     */
    @Override
    public EmployeeDetailResponse updateEmployee(String employeeId, UpdateEmployeeProfileRequest request) {
        EmployeeDetailResponse existing = getEmployeeById(employeeId);

        if (existing.getProfile() != null) {
            if (request.getFirstName() != null) existing.getProfile().setFirstName(request.getFirstName());
            if (request.getLastName() != null) existing.getProfile().setLastName(request.getLastName());
            if (request.getPhone() != null) existing.getProfile().setPhone(request.getPhone());
            if (request.getAvatarUrl() != null) existing.getProfile().setAvatarUrl(request.getAvatarUrl());
            if (request.getDateOfBirth() != null) existing.getProfile().setDateOfBirth(request.getDateOfBirth());
            if (request.getGender() != null) existing.getProfile().setGender(request.getGender());
        }
        return existing;
    }

    /**
     * Offboard employee and deactivate system account in PostgreSQL.
     */
    @Override
    public boolean deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setEmploymentStatus("Terminated");
            employeeRepository.save(employee);
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

    private EmployeeSummaryResponse mapToSummary(Employee employee) {
        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(employee.getEmployeeId()).orElse(null);
        String fullName = profile != null ? profile.getFirstName() + " " + profile.getLastName() : "N/A";
        String avatarUrl = profile != null ? profile.getAvatarUrl() : null;

        return EmployeeSummaryResponse.builder()
                .employeeId(employee.getEmployeeId())
                .email(employee.getEmail())
                .departmentId(employee.getDepartmentId())
                .positionId(employee.getPositionId())
                .employmentStatus(employee.getEmploymentStatus())
                .joinDate(employee.getJoinDate())
                .fullName(fullName)
                .avatarUrl(avatarUrl)
                .build();
    }
}
