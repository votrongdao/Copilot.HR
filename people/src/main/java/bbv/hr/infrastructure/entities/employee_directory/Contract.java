package bbv.hr.infrastructure.entities.employee_directory;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contract {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "contract_id", nullable = false, length = 50)
    private String contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "contract_number", nullable = false, unique = true, length = 50)
    private String contractNumber;

    @Column(name = "contract_type", length = 50)
    private String contractType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "base_salary", precision = 12, scale = 2)
    private BigDecimal baseSalary;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "signed_document_url", length = 255)
    private String signedDocumentUrl;
}
