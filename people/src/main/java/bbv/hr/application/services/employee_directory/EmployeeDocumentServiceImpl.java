package bbv.hr.application.services.employee_directory;

import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeDocumentRepository;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for Employee Document management operations.
 */
@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeDocumentServiceImpl(EmployeeDocumentRepository employeeDocumentRepository,
                                      EmployeeRepository employeeRepository) {
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Fetch all uploaded verification documents for a given employee ID.
     */
    @Override
    public List<EmployeeDocument> getEmployeeDocuments(String employeeId) {
        return employeeDocumentRepository.findByEmployeeId(employeeId);
    }

    /**
     * Upload a new verification document file for an employee.
     */
    @Override
    public EmployeeDocument uploadDocument(String employeeId, EmployeeDocument document) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        document.setEmployee(employee);
        if (document.getUploadedAt() == null) {
            document.setUploadedAt(LocalDateTime.now());
        }
        return document;
    }
}
