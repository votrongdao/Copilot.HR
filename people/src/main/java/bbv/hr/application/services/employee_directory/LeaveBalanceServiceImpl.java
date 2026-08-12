package bbv.hr.application.services.employee_directory;

import bbv.hr.application.interfaces.employee_directory.LeaveBalanceService;
import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;
import bbv.hr.infrastructure.entities.employee_directory.LeaveType;
import bbv.hr.infrastructure.repositories.employee_directory.EmployeeLeaveBalanceRepository;
import bbv.hr.infrastructure.repositories.employee_directory.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for Leave Quota and Balance operations.
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
     * Retrieve annual leave quotas and remaining balances for a given employee ID.
     */
    @Override
    public List<EmployeeLeaveBalance> getLeaveBalance(String employeeId) {
        List<EmployeeLeaveBalance> balances = employeeLeaveBalanceRepository.findByEmployeeId(employeeId);
        for (EmployeeLeaveBalance b : balances) {
            if (b.getLeaveType() != null && b.getLeaveType().getCode() != null) {
                LeaveType leaveType = leaveTypeRepository.findByCode(b.getLeaveType().getCode());
                if (leaveType != null) {
                    b.setLeaveType(leaveType);
                }
            }
        }
        return balances;
    }
}
