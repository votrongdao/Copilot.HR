package bbv.hr.api.dtos.organization.responses;

import bbv.hr.api.dtos.employee_directory.responses.EmployeeSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDetailResponse {

    private String departmentId;
    private String departmentName;
    private String parentDepartmentId;
    private String departmentLeadId;
    private String departmentLeadName;
    private String branchId;
    private String branchName;
    private Integer headcount;
    private Integer currentHeadcount;
    private String status;
    private List<EmployeeSummaryResponse> roster;
}
