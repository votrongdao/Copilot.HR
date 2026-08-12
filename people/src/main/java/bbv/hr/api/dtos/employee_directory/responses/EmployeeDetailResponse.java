package bbv.hr.api.dtos.employee_directory.responses;

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
public class EmployeeDetailResponse {

    private String employeeId;
    private String email;
    private String departmentId;
    private String positionId;
    private String employmentStatus;
    private LocalDate joinDate;
    private EmployeeProfileResponse profile;
    private List<EducationResponse> educations;
    private List<CertificationResponse> certifications;
    private List<AssetResponse> assets;
}
