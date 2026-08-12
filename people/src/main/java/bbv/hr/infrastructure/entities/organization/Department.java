package bbv.hr.infrastructure.entities.organization;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"parentDepartment", "departmentLead", "branch"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "department_id", nullable = false, length = 50)
    private String departmentId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_lead_id")
    private Employee departmentLead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private CompanyBranch branch;

    @Column(name = "budget_headcount")
    private Integer budgetHeadcount;

    @Column(name = "status", length = 50)
    private String status;
}
