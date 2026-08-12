package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for EmployeeDocument entity querying GetData component with in-memory caching.
 */
@Repository
public class EmployeeDocumentRepository {

    private final GetData getData;
    private final List<EmployeeDocument> documents = new ArrayList<>();

    public EmployeeDocumentRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all verification document records lazily cached from JSON mock data.
     */
    public List<EmployeeDocument> findAll() {
        if (documents.isEmpty()) {
            List<EmployeeDocument> loaded = getData.getEmployeeDirectoryEntities("employee_document", EmployeeDocument.class);
            if (loaded != null) {
                documents.addAll(loaded);
            }
        }
        return documents;
    }

    /**
     * Find all uploaded verification documents for a specific employee ID.
     */
    public List<EmployeeDocument> findByEmployeeId(String employeeId) {
        return documents.stream()
                .filter(d -> d.getEmployee() != null && d.getEmployee().getEmployeeId() != null
                        && d.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }
}
