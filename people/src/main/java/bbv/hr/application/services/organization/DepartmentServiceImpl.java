package bbv.hr.application.services.organization;

import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import bbv.hr.api.dtos.organization.requests.CreateDepartmentRequest;
import bbv.hr.api.dtos.organization.requests.DepartmentRestructureRequest;
import bbv.hr.api.dtos.organization.requests.UpdateDepartmentRequest;
import bbv.hr.api.dtos.organization.responses.DepartmentDetailResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentRestructureResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentTreeResponse;
import bbv.hr.application.interfaces.organization.DepartmentService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.organization.CompanyBranch;
import bbv.hr.infrastructure.entities.organization.Department;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import bbv.hr.infrastructure.repositories.organization.CompanyBranchRepository;
import bbv.hr.infrastructure.repositories.organization.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for Department management querying PostgreSQL database via JPA.
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyBranchRepository companyBranchRepository;

    /** Fetch organizational hierarchy tree of departments. */
    @Override
    public List<DepartmentTreeResponse> getOrgHierarchyTree() {
        List<Department> rootDepartments = departmentRepository.findByParentDepartmentIsNull();
        return rootDepartments.stream()
                .map(this::mapToTreeResponse)
                .collect(Collectors.toList());
    }

    /** Register a new operational department. */
    @Override
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        Department parent = null;
        if (request.getParentDepartmentId() != null && !request.getParentDepartmentId().isBlank()) {
            parent = departmentRepository.findById(request.getParentDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent department not found: " + request.getParentDepartmentId()));
        }

        Employee lead = null;
        if (request.getDepartmentLeadId() != null && !request.getDepartmentLeadId().isBlank()) {
            lead = employeeRepository.findById(request.getDepartmentLeadId())
                    .orElse(null);
        }

        CompanyBranch branch = null;
        if (request.getBranchId() != null && !request.getBranchId().isBlank()) {
            branch = companyBranchRepository.findById(request.getBranchId())
                    .orElse(null);
        }

        Department department = Department.builder()
                .departmentId(request.getDepartmentId() != null ? request.getDepartmentId() : "DEPT-" + UUID.randomUUID().toString().substring(0, 8))
                .departmentName(request.getDepartmentName())
                .parentDepartment(parent)
                .departmentLead(lead)
                .branch(branch)
                .headcount(request.getHeadcount())
                .status(request.getStatus() != null ? request.getStatus() : "Active")
                .build();

        Department saved = departmentRepository.save(department);
        return mapToResponse(saved);
    }

    /** Fetch department roster and details by department ID. */
    @Override
    public DepartmentDetailResponse getDepartmentById(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        List<Employee> members = employeeRepository.findAll().stream()
                .filter(e -> departmentId.equals(e.getDepartmentId()))
                .collect(Collectors.toList());

        List<EmployeeSummaryResponse> roster = members.stream()
                .map(e -> EmployeeSummaryResponse.builder()
                        .employeeId(e.getEmployeeId())
                        .email(e.getEmail())
                        .departmentId(departmentId)
                        .positionId(e.getPositionId())
                        .employmentStatus(e.getEmploymentStatus())
                        .joinDate(e.getJoinDate())
                        .build())
                .collect(Collectors.toList());

        return DepartmentDetailResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .parentDepartmentId(department.getParentDepartment() != null ? department.getParentDepartment().getDepartmentId() : null)
                .departmentLeadId(department.getDepartmentLead() != null ? department.getDepartmentLead().getEmployeeId() : null)
                .branchId(department.getBranch() != null ? department.getBranch().getBranchId() : null)
                .branchName(department.getBranch() != null ? department.getBranch().getBranchName() : null)
                .headcount(department.getHeadcount())
                .currentHeadcount(roster.size())
                .status(department.getStatus())
                .roster(roster)
                .build();
    }

    /** Update department metadata and validate parent cyclic dependencies. */
    @Override
    public DepartmentResponse updateDepartment(String departmentId, UpdateDepartmentRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        if (request.getParentDepartmentId() != null && !request.getParentDepartmentId().isBlank()) {
            if (request.getParentDepartmentId().equals(departmentId) || isCyclicParent(departmentId, request.getParentDepartmentId())) {
                throw new IllegalArgumentException("Parent department ID causes a cyclic dependency: " + request.getParentDepartmentId());
            }
            Department parent = departmentRepository.findById(request.getParentDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent department not found: " + request.getParentDepartmentId()));
            department.setParentDepartment(parent);
        } else {
            department.setParentDepartment(null);
        }

        if (request.getDepartmentName() != null) {
            department.setDepartmentName(request.getDepartmentName());
        }
        if (request.getHeadcount() != null) {
            department.setHeadcount(request.getHeadcount());
        }
        if (request.getStatus() != null) {
            department.setStatus(request.getStatus());
        }

        Department updated = departmentRepository.save(department);
        return mapToResponse(updated);
    }

    /** Delete a department if it has no active members. */
    @Override
    public boolean deleteDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        boolean hasActiveMembers = employeeRepository.findAll().stream()
                .anyMatch(e -> departmentId.equals(e.getDepartmentId()));

        if (hasActiveMembers) {
            throw new IllegalArgumentException("Department has active members and cannot be deleted");
        }

        departmentRepository.delete(department);
        return true;
    }

    /** Queue executive restructuring event for approval. */
    @Override
    public DepartmentRestructureResponse requestRestructure(DepartmentRestructureRequest request) {
        String taskId = "TASK-RESTRUCT-" + UUID.randomUUID().toString().substring(0, 8);
        return DepartmentRestructureResponse.builder()
                .taskId(taskId)
                .sourceDepartmentId(request.getSourceDepartmentId())
                .targetDepartmentId(request.getTargetDepartmentId())
                .actionType(request.getActionType())
                .effectiveDate(request.getEffectiveDate())
                .status("QUEUED")
                .message("Restructuring approval task queued successfully")
                .build();
    }

    private boolean isCyclicParent(String targetDeptId, String prospectiveParentId) {
        Set<String> visited = new HashSet<>();
        String currentParentId = prospectiveParentId;
        while (currentParentId != null) {
            if (currentParentId.equals(targetDeptId)) {
                return true;
            }
            if (!visited.add(currentParentId)) {
                break;
            }
            Optional<Department> parentOpt = departmentRepository.findById(currentParentId);
            if (parentOpt.isPresent() && parentOpt.get().getParentDepartment() != null) {
                currentParentId = parentOpt.get().getParentDepartment().getDepartmentId();
            } else {
                break;
            }
        }
        return false;
    }

    private DepartmentResponse mapToResponse(Department department) {
        return DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .parentDepartmentId(department.getParentDepartment() != null ? department.getParentDepartment().getDepartmentId() : null)
                .departmentLeadId(department.getDepartmentLead() != null ? department.getDepartmentLead().getEmployeeId() : null)
                .branchId(department.getBranch() != null ? department.getBranch().getBranchId() : null)
                .headcount(department.getHeadcount())
                .status(department.getStatus())
                .build();
    }

    private DepartmentTreeResponse mapToTreeResponse(Department department) {
        List<Department> children = departmentRepository.findByParentDepartmentDepartmentId(department.getDepartmentId());
        List<DepartmentTreeResponse> childResponses = children.stream()
                .map(this::mapToTreeResponse)
                .collect(Collectors.toList());

        return DepartmentTreeResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .parentDepartmentId(department.getParentDepartment() != null ? department.getParentDepartment().getDepartmentId() : null)
                .departmentLeadId(department.getDepartmentLead() != null ? department.getDepartmentLead().getEmployeeId() : null)
                .branchId(department.getBranch() != null ? department.getBranch().getBranchId() : null)
                .headcount(department.getHeadcount())
                .status(department.getStatus())
                .children(childResponses)
                .build();
    }
}
