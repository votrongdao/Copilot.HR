package bbv.hr.application.services.employee_directory;

import bbv.hr.api.dtos.employee_directory.responses.EmployeeLeaveBalanceResponse;
import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;
import bbv.hr.infrastructure.entities.employee_directory.LeaveType;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeLeaveBalanceRepository;
import bbv.hr.infrastructure.repositories.employee_directory.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Leave Quota and Balance operations querying PostgreSQL.
 */
@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveBalanceServiceImpl(EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository,
                                  LeaveTypeRepository leaveTypeRepository) {
        this.employeeLeaveBalanceRepository = employeeLeaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Retrieve annual leave quotas and remaining balances from PostgreSQL for a given employee ID.
     */
    @Override
    public List<EmployeeLeaveBalanceResponse> getLeaveBalance(String employeeId) {
        List<EmployeeLeaveBalance> balances = employeeLeaveBalanceRepository.findByEmployeeId(employeeId);

        return balances.stream().map(b -> {
            String leaveTypeCode = b.getLeaveType() != null ? b.getLeaveType().getCode() : "ANNUAL";
            String leaveTypeName = "Annual Leave";
            if (b.getLeaveType() != null && b.getLeaveType().getCode() != null) {
                LeaveType leaveType = leaveTypeRepository.findByCode(b.getLeaveType().getCode()).orElse(null);
                if (leaveType != null && leaveType.getName() != null) {
                    leaveTypeName = leaveType.getName();
                }
            }

            BigDecimal allocated = b.getAllocatedDays() != null ? b.getAllocatedDays() : BigDecimal.ZERO;
            BigDecimal used = b.getUsedDays() != null ? b.getUsedDays() : BigDecimal.ZERO;
            BigDecimal carried = b.getCarriedOverDays() != null ? b.getCarriedOverDays() : BigDecimal.ZERO;
            BigDecimal remaining = allocated.add(carried).subtract(used);

            return EmployeeLeaveBalanceResponse.builder()
                    .balanceId(b.getBalanceId())
                    .leaveTypeCode(leaveTypeCode)
                    .leaveTypeName(leaveTypeName)
                    .totalQuota(allocated)
                    .usedDays(used)
                    .remainingDays(remaining)
                    .carriedOverDays(carried)
                    .build();
        }).collect(Collectors.toList());
    }
}
