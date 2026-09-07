package bbv.hr.application.services.organization;

import bbv.hr.api.dtos.organization.requests.CreatePositionRequest;
import bbv.hr.api.dtos.organization.responses.PositionResponse;
import bbv.hr.application.interfaces.organization.PositionService;
import bbv.hr.infrastructure.entities.organization.Department;
import bbv.hr.infrastructure.entities.organization.Position;
import bbv.hr.infrastructure.repositories.organization.DepartmentRepository;
import bbv.hr.infrastructure.repositories.organization.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for Position management querying PostgreSQL database via JPA.
 */
@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;

    /** List all active position titles and salary bands. */
    @Override
    public List<PositionResponse> getPositions(String departmentId) {
        List<Position> positions;
        if (departmentId != null && !departmentId.isBlank()) {
            positions = positionRepository.findByDepartmentDepartmentId(departmentId);
        } else {
            positions = positionRepository.findAll();
        }
        return positions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /** Create position title and validate salary band range. */
    @Override
    public PositionResponse createPosition(CreatePositionRequest request) {
        if (request.getMinSalary() != null && request.getMaxSalary() != null
                && request.getMinSalary().compareTo(request.getMaxSalary()) > 0) {
            throw new IllegalArgumentException("Min salary cannot be greater than max salary");
        }

        Department department = null;
        if (request.getDepartmentId() != null && !request.getDepartmentId().isBlank()) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElse(null);
        }

        Position position = Position.builder()
                .positionId(request.getPositionId() != null ? request.getPositionId() : "POS-" + UUID.randomUUID().toString().substring(0, 8))
                .positionTitle(request.getPositionTitle())
                .level(request.getLevel())
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .department(department)
                .targetHeadcount(request.getTargetHeadcount())
                .status(request.getStatus() != null ? request.getStatus() : "Active")
                .build();

        Position saved = positionRepository.save(position);
        return mapToResponse(saved);
    }

    private PositionResponse mapToResponse(Position position) {
        return PositionResponse.builder()
                .positionId(position.getPositionId())
                .positionTitle(position.getPositionTitle())
                .level(position.getLevel())
                .minSalary(position.getMinSalary())
                .maxSalary(position.getMaxSalary())
                .departmentId(position.getDepartment() != null ? position.getDepartment().getDepartmentId() : null)
                .departmentName(position.getDepartment() != null ? position.getDepartment().getDepartmentName() : null)
                .targetHeadcount(position.getTargetHeadcount())
                .status(position.getStatus())
                .build();
    }
}
