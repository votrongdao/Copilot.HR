package bbv.hr.api.dtos.organization.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionResponse {

    private String positionId;
    private String positionTitle;
    private String level;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String departmentId;
    private String departmentName;
    private Integer targetHeadcount;
    private String status;
}
