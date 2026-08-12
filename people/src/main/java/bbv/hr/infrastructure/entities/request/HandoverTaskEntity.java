package bbv.hr.infrastructure.entities.request;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "handover_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "request")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HandoverTaskEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "task_id", nullable = false, length = 50)
    private String taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private TicketRequestEntity request;

    @Column(name = "task_title", length = 150)
    private String taskTitle;

    @Column(name = "handover_notes", columnDefinition = "TEXT")
    private String handoverNotes;

    @Column(name = "status", length = 50)
    private String status;
}
