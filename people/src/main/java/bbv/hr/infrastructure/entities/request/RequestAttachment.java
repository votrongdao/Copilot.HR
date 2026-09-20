package bbv.hr.infrastructure.entities.request;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "request")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestAttachment {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "attachment_id", nullable = false, length = 50)
    private String attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private TicketRequest request;

    @Column(name = "file_name", length = 150)
    private String fileName;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    @Column(name = "file_size_bytes")
    private Integer fileSizeBytes;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
