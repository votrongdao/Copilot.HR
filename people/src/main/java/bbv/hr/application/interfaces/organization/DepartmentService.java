package bbv.hr.application.interfaces.organization;

import bbv.hr.api.dtos.organization.requests.CreateDepartmentRequest;
import bbv.hr.api.dtos.organization.requests.DepartmentRestructureRequest;
import bbv.hr.api.dtos.organization.requests.UpdateDepartmentRequest;
import bbv.hr.api.dtos.organization.responses.DepartmentDetailResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentRestructureResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentTreeResponse;

import java.util.List;

/**
 * Service interface for Department management operations.
 */
public interface DepartmentService {

    /** Fetch organizational hierarchy tree of departments. */
    List<DepartmentTreeResponse> getOrgHierarchyTree();

    /** Register a new operational department. */
    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    /** Fetch department roster and details by department ID. */
    DepartmentDetailResponse getDepartmentById(String departmentId);

    /** Update department metadata and validate parent cyclic dependencies. */
    DepartmentResponse updateDepartment(String departmentId, UpdateDepartmentRequest request);

    /** Delete a department if it has no active members. */
    boolean deleteDepartment(String departmentId);

    /** Queue executive restructuring event for approval. */
    DepartmentRestructureResponse requestRestructure(DepartmentRestructureRequest request);
}
