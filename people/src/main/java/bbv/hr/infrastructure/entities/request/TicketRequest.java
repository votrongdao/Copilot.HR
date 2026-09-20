package bbv.hr.infrastructure.entities.request;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"requester", "requestType", "handoverEmployee"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketRequest {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "request_id", nullable = false, length = 50)
    private String requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private Employee requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @Column(name = "priority", length = 50)
    private String priority;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handover_employee_id")
    private Employee handoverEmployee;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration_days", precision = 5, scale = 2)
    private BigDecimal durationDays;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;
}
