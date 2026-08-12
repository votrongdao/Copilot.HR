package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDocumentResponse {

    private String documentId;
    private String documentType;
    private String documentName;
    private String documentUrl;
    private LocalDateTime uploadedAt;
}
