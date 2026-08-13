package bbv.hr.api.controllers.organization;

import bbv.hr.api.dtos.organization.requests.UpdateTeamMemberAllocationRequest;
import bbv.hr.api.dtos.organization.responses.TeamMemberResponse;
import bbv.hr.api.dtos.organization.responses.TeamResponse;
import bbv.hr.application.interfaces.organization.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Project Teams and Member Allocations.
 */
@Tag(name = "Organization & Department", description = "Project team listing and member allocation management APIs")
@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @Operation(
            summary = "List Project Teams & Members",
            description = "Lists active project teams and their allocated team members. Optionally filtered by department ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Project teams retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TeamResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<TeamResponse>> getTeams(
            @Parameter(description = "Department ID filter", example = "DEPT-ENG") @RequestParam(required = false) String departmentId
    ) {
        List<TeamResponse> teams = teamService.getTeams(departmentId);
        return ResponseEntity.ok(teams);
    }

    @Operation(
            summary = "Manage Team Member Allocation",
            description = "Adds, updates, or removes an employee member allocation and time percentage in a project team."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Team member allocation updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TeamMemberResponse.class),
                            examples = @ExampleObject(
                                    name = "AllocationExample",
                                    value = "{\"memberId\":\"TM-A1B2\",\"teamId\":\"TEAM-MOBILE\",\"teamName\":\"Mobile Core Team\",\"employeeId\":\"EMP-001\",\"roleInTeam\":\"Tech Lead\",\"allocationPercentage\":100.00,\"joinedDate\":\"2026-01-15\",\"status\":\"Active\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Team or Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Team not found with ID: TEAM-INVALID\"}"))
            )
    })
    @PostMapping("/{id}/members")
    public ResponseEntity<TeamMemberResponse> updateTeamMembers(
            @Parameter(description = "Team ID", example = "TEAM-MOBILE") @PathVariable("id") String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Team member allocation payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateTeamMemberAllocationRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"employeeId\":\"EMP-001\",\"roleInTeam\":\"Tech Lead\",\"allocationPercentage\":100.00,\"joinedDate\":\"2026-01-15\",\"status\":\"Active\"}"
                            )
                    )
            )
            @RequestBody UpdateTeamMemberAllocationRequest request
    ) {
        TeamMemberResponse response = teamService.updateTeamMemberAllocation(id, request);
        return ResponseEntity.ok(response);
    }
}
