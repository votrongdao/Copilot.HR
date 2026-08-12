package bbv.hr.infrastructure.entities.organization;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "department")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PositionEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "position_id", nullable = false, length = 50)
    private String positionId;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "job_level", length = 50)
    private String jobLevel;

    @Column(name = "min_salary", precision = 12, scale = 2)
    private BigDecimal minSalary;

    @Column(name = "max_salary", precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @Column(name = "target_headcount")
    private Integer targetHeadcount;

    @Column(name = "status", length = 50)
    private String status;
}
