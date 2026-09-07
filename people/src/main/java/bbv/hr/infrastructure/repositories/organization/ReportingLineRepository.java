package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.ReportingLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for ReportingLine entity querying PostgreSQL database.
 */
@Repository
public interface ReportingLineRepository extends JpaRepository<ReportingLine, String> {

    /** Find reporting line by line ID. */
    @Query("SELECT re FROM ReportingLine re WHERE re.lineId = :lineId")
    Optional<ReportingLine> findByLineId(@Param("lineId") String lineId);

    /** Find reporting lines for a specific employee. */
    List<ReportingLine> findByEmployeeEmployeeId(String employeeId);

    /** Find direct reports for a specific manager. */
    List<ReportingLine> findByManagerEmployeeId(String managerId);

    /** Check if reporting line relation exists between employee and manager. */
    boolean existsByEmployeeEmployeeIdAndManagerEmployeeId(String employeeId, String managerId);
}
