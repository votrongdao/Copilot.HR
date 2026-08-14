package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.CompanyBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for CompanyBranch entity querying PostgreSQL database.
 */
@Repository
public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, String> {

    /** Find company branch by branch ID. */
    @Query("SELECT b FROM CompanyBranch b WHERE b.branchId = :branchId")
    Optional<CompanyBranch> findByBranchId(@Param("branchId") String branchId);

    /** Find company branch by branch code. */
    Optional<CompanyBranch> findByBranchCode(String branchCode);

    /** Find company branches by status. */
    List<CompanyBranch> findByStatus(String status);
}
