package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Team entity querying PostgreSQL database.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    /** Find team by team ID. */
    @Query("SELECT t FROM Team t WHERE t.teamId = :teamId")
    Optional<Team> findByTeamId(@Param("teamId") String teamId);

    /** Find project teams by department ID. */
    List<Team> findByDepartmentDepartmentId(String departmentId);

    /** Find teams led by a specific employee. */
    List<Team> findByTeamLeadEmployeeId(String teamLeadId);

    /** Find teams by status. */
    List<Team> findByStatus(String status);
}
