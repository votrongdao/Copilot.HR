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
public class UpdateEmployeeProfileRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String avatarUrl;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
}
