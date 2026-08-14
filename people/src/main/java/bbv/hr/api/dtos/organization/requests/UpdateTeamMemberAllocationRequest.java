package bbv.hr.api.dtos.organization.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTeamMemberAllocationRequest {

    private String employeeId;
    private String roleInTeam;
    private BigDecimal allocationPercentage;
    private LocalDate joinedDate;
    private String status;
}
