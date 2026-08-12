package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeDocumentEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "document_id", nullable = false, length = 50)
    private String documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "document_name", length = 150)
    private String documentName;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
