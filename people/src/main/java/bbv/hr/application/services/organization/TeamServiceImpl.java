package bbv.hr.application.services.organization;

import bbv.hr.api.dtos.organization.requests.UpdateTeamMemberAllocationRequest;
import bbv.hr.api.dtos.organization.responses.TeamMemberResponse;
import bbv.hr.api.dtos.organization.responses.TeamResponse;
import bbv.hr.application.interfaces.organization.TeamService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.organization.Team;
import bbv.hr.infrastructure.entities.organization.TeamMember;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import bbv.hr.infrastructure.repositories.organization.TeamMemberRepository;
import bbv.hr.infrastructure.repositories.organization.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for Project Team management querying PostgreSQL database via JPA.
 */
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final EmployeeRepository employeeRepository;

    /** List all active project teams and member allocations. */
    @Override
    public List<TeamResponse> getTeams(String departmentId) {
        List<Team> teams;
        if (departmentId != null && !departmentId.isBlank()) {
            teams = teamRepository.findByDepartmentDepartmentId(departmentId);
        } else {
            teams = teamRepository.findAll();
        }
        return teams.stream()
                .map(this::mapToTeamResponse)
                .collect(Collectors.toList());
    }

    /** Manage team member allocation and percentage for a project team. */
    @Override
    public TeamMemberResponse updateTeamMemberAllocation(String teamId, UpdateTeamMemberAllocationRequest request) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + teamId));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + request.getEmployeeId()));

        Optional<TeamMember> existingOpt = teamMemberRepository.findByTeamTeamIdAndEmployeeEmployeeId(teamId, request.getEmployeeId());
        TeamMember member;

        if (existingOpt.isPresent()) {
            member = existingOpt.get();
            if ("REMOVE".equalsIgnoreCase(request.getStatus()) || "INACTIVE".equalsIgnoreCase(request.getStatus())) {
                teamMemberRepository.delete(member);
                return mapToMemberResponse(member);
            }
        } else {
            member = TeamMember.builder()
                    .memberId("TM-" + UUID.randomUUID().toString().substring(0, 8))
                    .team(team)
                    .employee(employee)
                    .build();
        }

        member.setRoleInTeam(request.getRoleInTeam());
        member.setAllocationPercentage(request.getAllocationPercentage());
        member.setJoinedDate(request.getJoinedDate());
        member.setStatus(request.getStatus() != null ? request.getStatus() : "Active");

        TeamMember saved = teamMemberRepository.save(member);
        return mapToMemberResponse(saved);
    }

    private TeamResponse mapToTeamResponse(Team team) {
        List<TeamMember> members = teamMemberRepository.findByTeamTeamId(team.getTeamId());
        List<TeamMemberResponse> memberResponses = members.stream()
                .map(this::mapToMemberResponse)
                .collect(Collectors.toList());

        return TeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .departmentId(team.getDepartment() != null ? team.getDepartment().getDepartmentId() : null)
                .departmentName(team.getDepartment() != null ? team.getDepartment().getDepartmentName() : null)
                .teamLeadId(team.getTeamLead() != null ? team.getTeamLead().getEmployeeId() : null)
                .description(team.getDescription())
                .status(team.getStatus())
                .members(memberResponses)
                .build();
    }

    private TeamMemberResponse mapToMemberResponse(TeamMember member) {
        return TeamMemberResponse.builder()
                .memberId(member.getMemberId())
                .teamId(member.getTeam() != null ? member.getTeam().getTeamId() : null)
                .teamName(member.getTeam() != null ? member.getTeam().getTeamName() : null)
                .employeeId(member.getEmployee() != null ? member.getEmployee().getEmployeeId() : null)
                .roleInTeam(member.getRoleInTeam())
                .allocationPercentage(member.getAllocationPercentage())
                .joinedDate(member.getJoinedDate())
                .status(member.getStatus())
                .build();
    }
}
