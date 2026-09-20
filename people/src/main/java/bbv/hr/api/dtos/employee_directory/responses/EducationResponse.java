package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse {

    private String educationId;
    private String institutionName;
    private String degree;
    private String major;
    private Integer startYear;
    private Integer endYear;
}
