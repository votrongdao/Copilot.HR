package bbv.hr.api.dtos.organization.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRestructureRequest {

    private String sourceDepartmentId;
    private String targetDepartmentId;
    private String actionType;
    private LocalDate effectiveDate;
    private String reason;
    private List<String> allocatedEmployeeIds;
}
