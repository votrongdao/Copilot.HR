package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationResponse {

    private String certificationId;
    private String certificateName;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expiryDate;
}
