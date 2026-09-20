package bbv.hr.application.services.employee_directory;

import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.api.dtos.employee_directory.responses.EmployeeDocumentResponse;
import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import bbv.hr.infrastructure.entities.employee_directory.Employee;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeDocumentRepository;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Employee Document management querying PostgreSQL.
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
     * Fetch all uploaded verification documents from PostgreSQL for a given employee ID.
     */
    @Override
    public List<EmployeeDocumentResponse> getEmployeeDocuments(String employeeId) {
        List<EmployeeDocument> documents = employeeDocumentRepository.findByEmployeeId(employeeId);
        return documents.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Upload a new verification document record into PostgreSQL for an employee.
     */
    @Override
    public EmployeeDocumentResponse uploadDocument(String employeeId, UploadDocumentRequest request) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        EmployeeDocument document = EmployeeDocument.builder()
                .documentId("DOC-" + System.currentTimeMillis())
                .employee(employee)
                .documentType(request.getDocumentType())
                .documentName(request.getDocumentName())
                .fileUrl(request.getDocumentUrl())
                .uploadedAt(LocalDateTime.now())
                .build();

        EmployeeDocument saved = employeeDocumentRepository.save(document);
        return mapToResponse(saved);
    }

    private EmployeeDocumentResponse mapToResponse(EmployeeDocument d) {
        return EmployeeDocumentResponse.builder()
                .documentId(d.getDocumentId())
                .documentType(d.getDocumentType())
                .documentName(d.getDocumentName())
                .documentUrl(d.getFileUrl())
                .uploadedAt(d.getUploadedAt())
                .build();
    }
}
