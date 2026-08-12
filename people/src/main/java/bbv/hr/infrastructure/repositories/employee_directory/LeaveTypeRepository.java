package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.LeaveType;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON-backed Repository for LeaveType entity querying GetData component with in-memory caching.
 */
@Repository
public class LeaveTypeRepository {

    private final GetData getData;
    private final List<LeaveType> leaveTypes = new ArrayList<>();

    public LeaveTypeRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all leave category configurations lazily cached from JSON mock data.
     */
    public List<LeaveType> findAll() {
        if (leaveTypes.isEmpty()) {
            List<LeaveType> loaded = getData.getEmployeeDirectoryEntities("leave_type", LeaveType.class);
            if (loaded != null) {
                leaveTypes.addAll(loaded);
            }
        }
        return leaveTypes;
    }

    /**
     * Find leave type by unique code (e.g. ANNUAL, SICK).
     */
    public LeaveType findByCode(String code) {
        return findAll().stream()
                .filter(l -> l.getCode() != null && l.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
