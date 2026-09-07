package bbv.hr.api.controllers.organization;

import bbv.hr.api.dtos.organization.requests.UpdateReportingLineRequest;
import bbv.hr.api.dtos.organization.responses.ReportingLineResponse;
import bbv.hr.application.interfaces.organization.ReportingLineService;
import io.swagger.v3.oas.annotations.Operation;
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
 * REST Controller for managing Supervisor Reporting Matrix.
 */
@Tag(name = "Organization & Department", description = "Supervisor reporting matrix and self-report check APIs")
@RestController
@RequestMapping("/api/v1/reporting-lines")
@RequiredArgsConstructor
public class ReportingLineController {

    private final ReportingLineService reportingLineService;

    @Operation(
            summary = "Fetch Supervisor Matrix",
            description = "Retrieves the system-wide supervisor matrix and direct/dotted reporting lines."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporting lines matrix fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportingLineResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ReportingLineResponse>> getReportingLines() {
        List<ReportingLineResponse> lines = reportingLineService.getReportingLines();
        return ResponseEntity.ok(lines);
    }

    @Operation(
            summary = "Update Supervisor & Check Self-Report",
            description = "Updates an employee's supervisor assignment. Validates that an employee cannot be assigned to report to self."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporting line updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportingLineResponse.class),
                            examples = @ExampleObject(
                                    name = "ReportingLineExample",
                                    value = "{\"lineId\":\"RL-7C8D\",\"employeeId\":\"EMP-002\",\"managerId\":\"EMP-001\",\"reportingType\":\"DIRECT\",\"effectiveDate\":\"2026-08-01\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Employee is assigned to self as manager",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Employee cannot be assigned as self manager\"}"))
            )
    })
    @PutMapping
    public ResponseEntity<ReportingLineResponse> updateReportingLine(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Reporting line assignment payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateReportingLineRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"employeeId\":\"EMP-002\",\"managerId\":\"EMP-001\",\"reportingType\":\"DIRECT\",\"effectiveDate\":\"2026-08-01\"}"
                            )
                    )
            )
            @RequestBody UpdateReportingLineRequest request
    ) {
        ReportingLineResponse response = reportingLineService.updateReportingLine(request);
        return ResponseEntity.ok(response);
    }
}
