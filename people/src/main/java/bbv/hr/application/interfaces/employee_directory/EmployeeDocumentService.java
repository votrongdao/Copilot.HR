package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;

import java.util.List;

/**
 * Service interface for Employee Document management operations.
 */
public interface EmployeeDocumentService {

    /**
     * Fetch all uploaded verification documents for a given employee ID.
     */
    List<EmployeeDocument> getEmployeeDocuments(String employeeId);

    /**
     * Upload a new verification document file for an employee.
     */
    EmployeeDocument uploadDocument(String employeeId, EmployeeDocument document);
}
