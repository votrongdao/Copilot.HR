package bbv.hr.api.dtos.organization.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReportingLineRequest {

    private String employeeId;
    private String managerId;
    private String reportingType;
    private LocalDate effectiveDate;
}
