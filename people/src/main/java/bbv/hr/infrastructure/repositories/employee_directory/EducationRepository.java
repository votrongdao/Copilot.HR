package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Education entity querying PostgreSQL database.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, String> {

    @Query("SELECT e FROM Education e WHERE e.employee.employeeId = :employeeId")
    List<Education> findByEmployeeId(@Param("employeeId") String employeeId);
}
