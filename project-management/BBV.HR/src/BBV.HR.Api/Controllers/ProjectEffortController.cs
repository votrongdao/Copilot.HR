using BBV.HR.Application.Common.Exceptions;
using BBV.HR.Application.Common.Models;
using BBV.HR.Application.DTOs.ProjectEffort;
using BBV.HR.Application.Interfaces.Services;
using Microsoft.AspNetCore.Mvc;

namespace BBV.HR.Api.Controllers;

[ApiController]
[Route("api/projects/{projectId:guid}")]
public class ProjectEffortController : ControllerBase
{
    private readonly IProjectEffortService _effortService;

    public ProjectEffortController(IProjectEffortService effortService)
    {
        _effortService = effortService;
    }

    [HttpGet("effort")]
    public async Task<ActionResult<ApiResponse<ProjectEffortSummaryDto>>> GetEffortSummary(Guid projectId)
    {
        var summary = await _effortService.GetProjectEffortSummaryAsync(projectId);
        if (summary == null) throw new NotFoundException("Project", projectId);
        return Ok(ApiResponse<ProjectEffortSummaryDto>.SuccessResult(summary));
    }

    [HttpGet("effort/members")]
    public async Task<ActionResult<ApiResponse<IEnumerable<MemberEffortDto>>>> GetMemberEffort(Guid projectId)
    {
        var memberEfforts = await _effortService.GetProjectMemberEffortAsync(projectId);
        return Ok(ApiResponse<IEnumerable<MemberEffortDto>>.SuccessResult(memberEfforts));
    }

    [HttpGet("time-entries")]
    public async Task<ActionResult<ApiResponse<IEnumerable<ProjectTimeEntryDto>>>> GetTimeEntries(Guid projectId)
    {
        var entries = await _effortService.GetProjectTimeEntriesAsync(projectId);
        return Ok(ApiResponse<IEnumerable<ProjectTimeEntryDto>>.SuccessResult(entries));
    }
}
