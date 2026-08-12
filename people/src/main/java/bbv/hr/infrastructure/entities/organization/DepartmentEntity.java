package bbv.hr.infrastructure.entities.organization;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeEntity;
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
public class DepartmentEntity {

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
    private DepartmentEntity parentDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_lead_id")
    private EmployeeEntity departmentLead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private CompanyBranchEntity branch;

    @Column(name = "budget_headcount")
    private Integer budgetHeadcount;

    @Column(name = "status", length = 50)
    private String status;
}
