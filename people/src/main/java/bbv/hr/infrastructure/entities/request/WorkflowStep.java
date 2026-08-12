package bbv.hr.infrastructure.entities.request;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "requestType")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowStep {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "step_id", nullable = false, length = 50)
    private String stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @Column(name = "step_order")
    private Integer stepOrder;

    @Column(name = "step_name", length = 100)
    private String stepName;

    @Column(name = "approver_role", length = 50)
    private String approverRole;
}
