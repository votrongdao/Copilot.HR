package bbv.hr.api.dtos.organization.responses;

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
public class TeamMemberResponse {

    private String memberId;
    private String teamId;
    private String teamName;
    private String employeeId;
    private String employeeName;
    private String roleInTeam;
    private BigDecimal allocationPercentage;
    private LocalDate joinedDate;
    private String status;
}
