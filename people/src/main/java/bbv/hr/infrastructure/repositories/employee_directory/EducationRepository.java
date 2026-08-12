package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Education;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Education entity querying GetData component with in-memory caching.
 */
@Repository
public class EducationRepository {

    private final GetData getData;
    private final List<Education> educations = new ArrayList<>();

    public EducationRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all education records lazily cached from JSON mock data.
     */
    public List<Education> findAll() {
        if (educations.isEmpty()) {
            List<Education> loaded = getData.getEmployeeDirectoryEntities("education", Education.class);
            if (loaded != null) {
                educations.addAll(loaded);
            }
        }
        return educations;
    }

    /**
     * Find all education records for a specific employee ID.
     */
    public List<Education> findByEmployeeId(String employeeId) {
        return educations.stream()
                .filter(e -> e.getEmployee() != null && e.getEmployee().getEmployeeId() != null
                        && e.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }
}
