package bbv.hr.application.interfaces.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;

import java.util.List;

/**
 * Service interface for Employee Document management operations.
 */
public interface EmployeeDocumentService {

    /**
     * Fetch all uploaded verification documents for a given employee ID.
     */
    List<EmployeeDocumentResponse> getEmployeeDocuments(String employeeId);

    /**
     * Upload a new verification document file for an employee.
     */
    EmployeeDocumentResponse uploadDocument(String employeeId, UploadDocumentRequest request);
}
