package bbv.hr.api.dtos.organization.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRestructureResponse {

    private String taskId;
    private String sourceDepartmentId;
    private String targetDepartmentId;
    private String actionType;
    private LocalDate effectiveDate;
    private String status;
    private String message;
}
