package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSummaryResponse {

    private String employeeId;
    private String email;
    private String departmentId;
    private String positionId;
    private String employmentStatus;
    private LocalDate joinDate;
    private String fullName;
    private String avatarUrl;
}
