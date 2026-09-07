package bbv.hr.application.services.organization;

import bbv.hr.api.dtos.organization.requests.UpdateReportingLineRequest;
import bbv.hr.api.dtos.organization.responses.ReportingLineResponse;
import bbv.hr.application.interfaces.organization.ReportingLineService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.organization.ReportingLine;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import bbv.hr.infrastructure.repositories.organization.ReportingLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for Reporting Line management querying PostgreSQL database via JPA.
 */
@Service
@RequiredArgsConstructor
public class ReportingLineServiceImpl implements ReportingLineService {

    private final ReportingLineRepository reportingLineRepository;
    private final EmployeeRepository employeeRepository;

    /** Fetch supervisor hierarchy matrix. */
    @Override
    public List<ReportingLineResponse> getReportingLines() {
        List<ReportingLine> lines = reportingLineRepository.findAll();
        return lines.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /** Update supervisor and validate self-report assignment error. */
    @Override
    public ReportingLineResponse updateReportingLine(UpdateReportingLineRequest request) {
        if (request.getEmployeeId() != null && request.getEmployeeId().equalsIgnoreCase(request.getManagerId())) {
            throw new IllegalArgumentException("Employee cannot be assigned as self manager");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + request.getEmployeeId()));

        Employee manager = employeeRepository.findById(request.getManagerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + request.getManagerId()));

        List<ReportingLine> existingLines = reportingLineRepository.findByEmployeeEmployeeId(request.getEmployeeId());
        ReportingLine line;

        if (!existingLines.isEmpty()) {
            line = existingLines.get(0);
        } else {
            line = ReportingLine.builder()
                    .lineId("RL-" + UUID.randomUUID().toString().substring(0, 8))
                    .employee(employee)
                    .build();
        }

        line.setManager(manager);
        line.setReportingType(request.getReportingType() != null ? request.getReportingType() : "DIRECT");
        line.setEffectiveDate(request.getEffectiveDate());

        ReportingLine saved = reportingLineRepository.save(line);
        return mapToResponse(saved);
    }

    private ReportingLineResponse mapToResponse(ReportingLine line) {
        return ReportingLineResponse.builder()
                .lineId(line.getLineId())
                .employeeId(line.getEmployee() != null ? line.getEmployee().getEmployeeId() : null)
                .managerId(line.getManager() != null ? line.getManager().getEmployeeId() : null)
                .reportingType(line.getReportingType())
                .effectiveDate(line.getEffectiveDate())
                .build();
    }
}
