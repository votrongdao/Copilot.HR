package bbv.hr.api.dtos.employee_directory.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContractRequest {

    private String contractNumber;
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal baseSalary;
    private String status;
}
