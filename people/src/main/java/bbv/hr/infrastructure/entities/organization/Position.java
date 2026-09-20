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
public class Position {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "position_id", nullable = false, length = 50)
    private String positionId;

    @Column(name = "position_title", length = 150)
    private String positionTitle;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "min_salary", precision = 12, scale = 2)
    private BigDecimal minSalary;

    @Column(name = "max_salary", precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "target_headcount")
    private Integer targetHeadcount;

    @Column(name = "status", length = 50)
    private String status;
}
