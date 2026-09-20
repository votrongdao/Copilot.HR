package bbv.hr.infrastructure.entities.request;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "approval_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"request", "step", "approver"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApprovalLog {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "log_id", nullable = false, length = 50)
    private String logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private TicketRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private WorkflowStep step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee approver;

    @Column(name = "action", length = 50)
    private String action;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "remaining_sla_minutes")
    private Integer remainingSlaMinutes;
}
