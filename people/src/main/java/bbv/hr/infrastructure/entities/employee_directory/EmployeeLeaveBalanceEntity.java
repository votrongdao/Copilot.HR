package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employee_leave_balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee", "leaveType"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeLeaveBalanceEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "balance_id", nullable = false, length = 50)
    private String balanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveTypeEntity leaveType;

    @Column(name = "year")
    private Integer year;

    @Column(name = "allocated_days", precision = 5, scale = 2)
    private BigDecimal allocatedDays;

    @Column(name = "carried_over_days", precision = 5, scale = 2)
    private BigDecimal carriedOverDays;

    @Column(name = "used_days", precision = 5, scale = 2)
    private BigDecimal usedDays;

    @Column(name = "pending_days", precision = 5, scale = 2)
    private BigDecimal pendingDays;

    @Column(name = "remaining_days", precision = 5, scale = 2)
    private BigDecimal remainingDays;
}
