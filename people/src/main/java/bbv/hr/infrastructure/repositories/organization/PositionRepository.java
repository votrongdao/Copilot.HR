package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Position entity querying PostgreSQL database.
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, String> {

    /** Find position by position ID. */
    @Query("SELECT p FROM Position p WHERE p.positionId = :positionId")
    Optional<Position> findByPositionId(@Param("positionId") String positionId);

    /** Find positions by department ID. */
    List<Position> findByDepartmentDepartmentId(String departmentId);

    /** Find positions by level. */
    List<Position> findByLevel(String level);

    /** Find positions by status. */
    List<Position> findByStatus(String status);
}
