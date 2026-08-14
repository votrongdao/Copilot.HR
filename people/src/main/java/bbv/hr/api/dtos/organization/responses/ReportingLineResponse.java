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
public class ReportingLineResponse {

    private String lineId;
    private String employeeId;
    private String employeeName;
    private String managerId;
    private String managerName;
    private String reportingType;
    private LocalDate effectiveDate;
}
