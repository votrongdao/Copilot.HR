package bbv.hr.api.dtos.employee_directory.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeRequest {

    private String employeeId;
    private String email;
    private String departmentId;
    private String positionId;
    private String firstName;
    private String lastName;
    private String phone;
    private String employmentStatus;
    private LocalDate joinDate;
}
