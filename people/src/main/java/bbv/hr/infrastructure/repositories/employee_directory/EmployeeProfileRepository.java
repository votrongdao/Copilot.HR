package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeProfile;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON-backed Repository for Employee Profile querying GetData component with in-memory caching.
 */
@Repository
public class EmployeeProfileRepository {

    private final GetData getData;
    private final List<EmployeeProfile> profiles = new ArrayList<>();

    public EmployeeProfileRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all employee profiles lazily cached from JSON mock data.
     */
    public List<EmployeeProfile> findAll() {
        if (profiles.isEmpty()) {
            List<EmployeeProfile> loaded = getData.getEmployeeDirectoryEntities("employee_profile", EmployeeProfile.class);
            if (loaded != null) {
                profiles.addAll(loaded);
            }
        }
        return profiles;
    }

    /**
     * Find employee profile by profile ID.
     */
    public EmployeeProfile findById(String profileId) {
        return findAll().stream()
                .filter(p -> p.getProfileId() != null && p.getProfileId().equalsIgnoreCase(profileId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find profile by associated employee ID.
     */
    public EmployeeProfile findByEmployeeId(String employeeId) {
        return findAll().stream()
                .filter(p -> p.getEmployee() != null && p.getEmployee().getEmployeeId() != null
                        && p.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .findFirst()
                .orElse(null);
    }
}
