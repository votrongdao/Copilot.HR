package bbv.hr.api.dtos.employee_directory.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDocumentRequest {

    private String documentType;
    private String documentName;
    private String documentUrl;
}
