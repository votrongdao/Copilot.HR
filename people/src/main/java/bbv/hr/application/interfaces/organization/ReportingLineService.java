package bbv.hr.application.interfaces.organization;

import bbv.hr.api.dtos.organization.requests.UpdateReportingLineRequest;
import bbv.hr.api.dtos.organization.responses.ReportingLineResponse;

import java.util.List;

/**
 * Service interface for Reporting Line management operations.
 */
public interface ReportingLineService {

    /** Fetch supervisor hierarchy matrix. */
    List<ReportingLineResponse> getReportingLines();

    /** Update supervisor and validate self-report assignment error. */
    ReportingLineResponse updateReportingLine(UpdateReportingLineRequest request);
}
