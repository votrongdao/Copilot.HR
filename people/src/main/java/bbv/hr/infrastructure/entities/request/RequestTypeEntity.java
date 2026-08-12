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
public class RequestTypeEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "type_id", nullable = false, length = 50)
    private String typeId;

    @Column(name = "type_code", nullable = false, unique = true, length = 50)
    private String typeCode;

    @Column(name = "type_name", length = 100)
    private String typeName;

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
