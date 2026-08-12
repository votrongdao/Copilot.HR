package bbv.hr.infrastructure.entities.organization;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompanyBranch {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "branch_id", nullable = false, length = 50)
    private String branchId;

    @Column(name = "branch_name", length = 100)
    private String branchName;

    @Column(name = "branch_code", nullable = false, unique = true, length = 50)
    private String branchCode;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "status", length = 50)
    private String status;
}
