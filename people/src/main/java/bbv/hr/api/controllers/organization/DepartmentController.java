package bbv.hr.api.controllers.organization;

import bbv.hr.api.dtos.organization.requests.CreateDepartmentRequest;
import bbv.hr.api.dtos.organization.requests.DepartmentRestructureRequest;
import bbv.hr.api.dtos.organization.requests.UpdateDepartmentRequest;
import bbv.hr.api.dtos.organization.responses.DepartmentDetailResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentRestructureResponse;
import bbv.hr.api.dtos.organization.responses.DepartmentTreeResponse;
import bbv.hr.application.interfaces.organization.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Departments and Organizational Hierarchy.
 */
@Tag(name = "Organization & Department", description = "Org hierarchy tree, department CRUD, restructuring, and cyclic parent checks")
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(
            summary = "Fetch Org Hierarchy Tree",
            description = "Retrieves top-level root departments and recursively expands their sub-department tree hierarchy."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Org hierarchy tree fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartmentTreeResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DepartmentTreeResponse>> getDepartments() {
        List<DepartmentTreeResponse> tree = departmentService.getOrgHierarchyTree();
        return ResponseEntity.ok(tree);
    }

    @Operation(
            summary = "Register Operational Department",
            description = "Registers a new department entity with headcount budget, lead employee ID, and optional parent department ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Department created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentResponse.class),
                            examples = @ExampleObject(
                                    name = "CreateDepartmentExample",
                                    value = "{\"departmentId\":\"DEPT-ENG\",\"departmentName\":\"Software Engineering\",\"parentDepartmentId\":\"DEPT-TECH\",\"departmentLeadId\":\"EMP-001\",\"branchId\":\"BRANCH-HQ\",\"headcount\":50,\"status\":\"Active\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payload or parent department not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Parent department not found: DEPT-INVALID\"}"))
            )
    })
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Department creation payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateDepartmentRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"departmentId\":\"DEPT-ENG\",\"departmentName\":\"Software Engineering\",\"parentDepartmentId\":\"DEPT-TECH\",\"departmentLeadId\":\"EMP-001\",\"branchId\":\"BRANCH-HQ\",\"headcount\":50,\"status\":\"Active\"}"
                            )
                    )
            )
            @RequestBody CreateDepartmentRequest request
    ) {
        DepartmentResponse response = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Fetch Department Roster & Details",
            description = "Fetches metadata for a specific department along with its active member roster."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Department details and roster fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Department not found with ID: DEPT-999\"}"))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDetailResponse> getDepartmentById(
            @Parameter(description = "Department ID", example = "DEPT-ENG") @PathVariable("id") String id
    ) {
        DepartmentDetailResponse response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update Department Metadata",
            description = "Updates metadata or parent department ID. Validates parent cyclic dependency errors."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Department updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parent department ID causes cyclic dependency loop",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Parent department ID causes a cyclic dependency: DEPT-SUB\"}"))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @Parameter(description = "Department ID to update", example = "DEPT-ENG") @PathVariable("id") String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Department update payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDepartmentRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"departmentName\":\"Core Software Engineering\",\"parentDepartmentId\":\"DEPT-TECH\",\"departmentLeadId\":\"EMP-002\",\"branchId\":\"BRANCH-HQ\",\"headcount\":60,\"status\":\"Active\"}"
                            )
                    )
            )
            @RequestBody UpdateDepartmentRequest request
    ) {
        DepartmentResponse response = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Prevent Deleting Active Dept",
            description = "Deletes a department entity if no active employee members belong to it."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Department deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Department has active members and cannot be deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Department has active members and cannot be deleted\"}"))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "Department ID to delete", example = "DEPT-ENG") @PathVariable("id") String id
    ) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Queue Executive Restructure Event",
            description = "Queues an executive department restructuring approval event (merge, split, or transfer)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Restructuring task queued successfully for approval",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentRestructureResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"taskId\":\"TASK-RESTRUCT-8A9B\",\"sourceDepartmentId\":\"DEPT-ENG\",\"targetDepartmentId\":\"DEPT-PROD\",\"actionType\":\"MERGE\",\"effectiveDate\":\"2026-09-01\",\"status\":\"QUEUED\",\"message\":\"Restructuring approval task queued successfully\"}"
                            )
                    )
            )
    })
    @PostMapping("/restructure")
    public ResponseEntity<DepartmentRestructureResponse> requestRestructure(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Restructuring request payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentRestructureRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"sourceDepartmentId\":\"DEPT-ENG\",\"targetDepartmentId\":\"DEPT-PROD\",\"actionType\":\"MERGE\",\"effectiveDate\":\"2026-09-01\",\"reason\":\"Q3 Organizational Consolidation\",\"allocatedEmployeeIds\":[\"EMP-001\",\"EMP-002\"]}"
                            )
                    )
            )
            @RequestBody DepartmentRestructureRequest request
    ) {
        DepartmentRestructureResponse response = departmentService.requestRestructure(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
