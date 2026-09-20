package bbv.hr.infrastructure.entities.request;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestType {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "request_type_id", nullable = false, length = 50)
    private String requestTypeId;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "default_sla_hours")
    private Integer defaultSlaHours;

    @Column(name = "requires_handover")
    private Boolean requiresHandover;

    @Column(name = "requires_attachment")
    private Boolean requiresAttachment;

    @Column(name = "is_active")
    private Boolean isActive;
}
