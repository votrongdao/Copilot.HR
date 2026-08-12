package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Certification;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Certification entity querying GetData component with in-memory caching.
 */
@Repository
public class CertificationRepository {

    private final GetData getData;
    private final List<Certification> certifications = new ArrayList<>();

    public CertificationRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all certification records lazily cached from JSON mock data.
     */
    public List<Certification> findAll() {
        if (certifications.isEmpty()) {
            List<Certification> loaded = getData.getEmployeeDirectoryEntities("certification", Certification.class);
            if (loaded != null) {
                certifications.addAll(loaded);
            }
        }
        return certifications;
    }

    /**
     * Find all professional certification credentials for a specific employee ID.
     */
    public List<Certification> findByEmployeeId(String employeeId) {
        return findAll().stream()
                .filter(c -> c.getEmployee() != null && c.getEmployee().getEmployeeId() != null
                        && c.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }
}
