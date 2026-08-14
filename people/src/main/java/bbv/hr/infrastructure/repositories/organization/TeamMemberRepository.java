package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for TeamMember entity querying PostgreSQL database.
 */
@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    /** Find team member allocation by member ID. */
    @Query("SELECT tm FROM TeamMember tm WHERE tm.memberId = :memberId")
    Optional<TeamMember> findByMemberId(@Param("memberId") String memberId);

    /** Find all member allocations for a specific team. */
    List<TeamMember> findByTeamTeamId(String teamId);

    /** Find all team allocations for a specific employee. */
    List<TeamMember> findByEmployeeEmployeeId(String employeeId);

    /** Find specific team member allocation by team ID and employee ID. */
    Optional<TeamMember> findByTeamTeamIdAndEmployeeEmployeeId(String teamId, String employeeId);

    /** Check if employee allocation exists in a team. */
    boolean existsByTeamTeamIdAndEmployeeEmployeeId(String teamId, String employeeId);

    /** Delete team member allocation by team ID and employee ID. */
    void deleteByTeamTeamIdAndEmployeeEmployeeId(String teamId, String employeeId);
}
