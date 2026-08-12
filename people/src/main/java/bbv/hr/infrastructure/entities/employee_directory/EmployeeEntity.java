package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "directManager")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "employee_id", nullable = false, length = 50)
    private String employeeId;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "employment_status", length = 50)
    private String employmentStatus;

    @Column(name = "department_id", length = 50)
    private String departmentId;

    @Column(name = "position_id", length = 50)
    private String positionId;

    @Column(name = "team_id", length = 50)
    private String teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direct_manager_id")
    private EmployeeEntity directManager;
}
