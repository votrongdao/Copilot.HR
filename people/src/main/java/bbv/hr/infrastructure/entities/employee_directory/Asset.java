package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Asset {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "asset_id", nullable = false, length = 50)
    private String assetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "asset_name", length = 150)
    private String assetName;

    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    private String serialNumber;

    @Column(name = "asset_type", length = 50)
    private String assetType;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "status", length = 50)
    private String status;
}
