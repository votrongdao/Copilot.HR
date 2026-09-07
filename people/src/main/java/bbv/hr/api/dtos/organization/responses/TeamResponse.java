package bbv.hr.api.dtos.organization.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponse {

    private String teamId;
    private String teamName;
    private String departmentId;
    private String departmentName;
    private String teamLeadId;
    private String teamLeadName;
    private String description;
    private String status;
    private List<TeamMemberResponse> members;
}
