package bbv.hr.infrastructure.entities.organization;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "team_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"team", "employee"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TeamMember {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "member_id", nullable = false, length = 50)
    private String memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "role_in_team", length = 100)
    private String roleInTeam;

    @Column(name = "allocation_percentage", precision = 5, scale = 2)
    private BigDecimal allocationPercentage;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "status", length = 50)
    private String status;
}
