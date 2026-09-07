package bbv.hr.api.dtos.organization.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePositionRequest {

    private String positionId;
    private String positionTitle;
    private String level;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String departmentId;
    private Integer targetHeadcount;
    private String status;
}
