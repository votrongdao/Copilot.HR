package bbv.hr.api.dtos.organization.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDepartmentRequest {

    private String departmentName;
    private String parentDepartmentId;
    private String departmentLeadId;
    private String branchId;
    private Integer headcount;
    private String status;
}
