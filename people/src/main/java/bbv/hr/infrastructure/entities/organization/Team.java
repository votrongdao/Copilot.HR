package bbv.hr.infrastructure.entities.organization;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"department", "teamLead"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Team {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "team_id", nullable = false, length = 50)
    private String teamId;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_lead_id")
    private Employee teamLead;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", length = 50)
    private String status;
}
