package bbv.hr.application.interfaces.organization;

import bbv.hr.api.dtos.organization.requests.CreatePositionRequest;
import bbv.hr.api.dtos.organization.responses.PositionResponse;

import java.util.List;

/**
 * Service interface for Position management operations.
 */
public interface PositionService {

    /** List all active position titles and salary bands. */
    List<PositionResponse> getPositions(String departmentId);

    /** Create position title and validate salary band range. */
    PositionResponse createPosition(CreatePositionRequest request);
}
