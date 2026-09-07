package bbv.hr.api.controllers.organization;

import bbv.hr.api.dtos.organization.requests.CreatePositionRequest;
import bbv.hr.api.dtos.organization.responses.PositionResponse;
import bbv.hr.application.interfaces.organization.PositionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Position Titles and Salary Bands.
 */
@Tag(name = "Organization & Department", description = "Position listing, creation, and salary band validation APIs")
@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @Operation(
            summary = "List Positions & Salary Bands",
            description = "Lists all positions, levels, and salary ranges. Optionally filtered by department ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Positions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PositionResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<PositionResponse>> getPositions(
            @Parameter(description = "Department ID filter", example = "DEPT-ENG") @RequestParam(required = false) String departmentId
    ) {
        List<PositionResponse> positions = positionService.getPositions(departmentId);
        return ResponseEntity.ok(positions);
    }

    @Operation(
            summary = "Create Position & Check Salary Range",
            description = "Creates a new position title and validates that min salary is less than or equal to max salary."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Position created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PositionResponse.class),
                            examples = @ExampleObject(
                                    name = "CreatePositionExample",
                                    value = "{\"positionId\":\"POS-SWE-SR\",\"positionTitle\":\"Senior Software Engineer\",\"level\":\"Senior L4\",\"minSalary\":3000.00,\"maxSalary\":5000.00,\"departmentId\":\"DEPT-ENG\",\"departmentName\":\"Software Engineering\",\"targetHeadcount\":15,\"status\":\"Active\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Min salary is greater than max salary",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Min salary cannot be greater than max salary\"}"))
            )
    })
    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Position creation payload",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePositionRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"positionId\":\"POS-SWE-SR\",\"positionTitle\":\"Senior Software Engineer\",\"level\":\"Senior L4\",\"minSalary\":3000.00,\"maxSalary\":5000.00,\"departmentId\":\"DEPT-ENG\",\"targetHeadcount\":15,\"status\":\"Active\"}"
                            )
                    )
            )
            @RequestBody CreatePositionRequest request
    ) {
        PositionResponse response = positionService.createPosition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
