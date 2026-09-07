package bbv.hr.application.interfaces.organization;

import bbv.hr.api.dtos.organization.requests.UpdateTeamMemberAllocationRequest;
import bbv.hr.api.dtos.organization.responses.TeamMemberResponse;
import bbv.hr.api.dtos.organization.responses.TeamResponse;

import java.util.List;

/**
 * Service interface for Project Team management operations.
 */
public interface TeamService {

    /** List all active project teams and member allocations. */
    List<TeamResponse> getTeams(String departmentId);

    /** Manage team member allocation and percentage for a project team. */
    TeamMemberResponse updateTeamMemberAllocation(String teamId, UpdateTeamMemberAllocationRequest request);
}
