package bbv.hr.infrastructure.entities.organization;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reporting_line")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee", "manager"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReportingLineEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "reporting_id", nullable = false, length = 50)
    private String reportingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private EmployeeEntity manager;

    @Column(name = "reporting_type", length = 50)
    private String reportingType;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;
}
