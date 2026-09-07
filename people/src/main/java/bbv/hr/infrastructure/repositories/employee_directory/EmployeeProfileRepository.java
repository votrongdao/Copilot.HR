package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for EmployeeProfile entity querying PostgreSQL database.
 */
@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, String> {

    @Query("SELECT p FROM EmployeeProfile p WHERE p.employee.employeeId = :employeeId")
    Optional<EmployeeProfile> findByEmployeeId(@Param("employeeId") String employeeId);
}
