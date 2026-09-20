package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLeaveBalanceResponse {

    private String balanceId;
    private String leaveTypeCode;
    private String leaveTypeName;
    private BigDecimal totalQuota;
    private BigDecimal usedDays;
    private BigDecimal remainingDays;
    private BigDecimal carriedOverDays;
}
