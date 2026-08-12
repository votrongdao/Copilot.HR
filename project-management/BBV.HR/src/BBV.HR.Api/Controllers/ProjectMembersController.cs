using BBV.HR.Application.Common.Exceptions;
using BBV.HR.Application.Common.Models;
using BBV.HR.Application.DTOs.ProjectMembers;
using BBV.HR.Application.Interfaces.Services;
using Microsoft.AspNetCore.Mvc;

namespace BBV.HR.Api.Controllers;

[ApiController]
[Route("api/projects/{projectId:guid}/members")]
public class ProjectMembersController : ControllerBase
{
    private readonly IProjectMemberService _memberService;

    public ProjectMembersController(IProjectMemberService memberService)
    {
        _memberService = memberService;
    }

    [HttpGet]
    public async Task<ActionResult<ApiResponse<IEnumerable<ProjectMemberDto>>>> GetMembers(Guid projectId)
    {
        var members = await _memberService.GetProjectMembersAsync(projectId);
        return Ok(ApiResponse<IEnumerable<ProjectMemberDto>>.SuccessResult(members));
    }

    [HttpGet("{memberId:guid}")]
    public async Task<ActionResult<ApiResponse<ProjectMemberDto>>> GetMemberById(Guid projectId, Guid memberId)
    {
        var member = await _memberService.GetProjectMemberByIdAsync(projectId, memberId);
        if (member == null) throw new NotFoundException("ProjectMember", memberId);
        return Ok(ApiResponse<ProjectMemberDto>.SuccessResult(member));
    }

    [HttpPost]
    public async Task<ActionResult<ApiResponse<ProjectMemberDto>>> AddMember(Guid projectId, [FromBody] AddProjectMemberDto dto)
    {
        var member = await _memberService.AddProjectMemberAsync(projectId, dto);
        return CreatedAtAction(nameof(GetMemberById), new { projectId, memberId = member.Id }, 
            ApiResponse<ProjectMemberDto>.SuccessResult(member, "Project member added successfully.", 201));
    }

    [HttpPatch("{memberId:guid}")]
    public async Task<ActionResult<ApiResponse<ProjectMemberDto>>> UpdateMember(Guid projectId, Guid memberId, [FromBody] UpdateProjectMemberDto dto)
    {
        var updated = await _memberService.UpdateProjectMemberAsync(projectId, memberId, dto);
        if (updated == null) throw new NotFoundException("ProjectMember", memberId);
        return Ok(ApiResponse<ProjectMemberDto>.SuccessResult(updated, "Project member updated successfully."));
    }

    [HttpDelete("{memberId:guid}")]
    public async Task<ActionResult<ApiResponse>> RemoveMember(Guid projectId, Guid memberId)
    {
        var removed = await _memberService.RemoveProjectMemberAsync(projectId, memberId);
        if (!removed) throw new NotFoundException("ProjectMember", memberId);
        return Ok(ApiResponse.SuccessResponse("Project member removed successfully."));
    }

    [HttpPatch("{memberId:guid}/allocation")]
    public async Task<ActionResult<ApiResponse<ProjectMemberDto>>> UpdateAllocation(Guid projectId, Guid memberId, [FromBody] UpdateMemberAllocationDto dto)
    {
        var updated = await _memberService.UpdateMemberAllocationAsync(projectId, memberId, dto);
        if (updated == null) throw new NotFoundException("ProjectMember", memberId);
        return Ok(ApiResponse<ProjectMemberDto>.SuccessResult(updated, "Member allocation updated successfully."));
    }
}
