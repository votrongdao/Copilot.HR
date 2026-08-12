package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "leave_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LeaveType {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "leave_type_id", nullable = false, length = 50)
    private String leaveTypeId;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "default_days_per_year", precision = 5, scale = 2)
    private BigDecimal defaultDaysPerYear;

    @Column(name = "status", length = 50)
    private String status;
}
