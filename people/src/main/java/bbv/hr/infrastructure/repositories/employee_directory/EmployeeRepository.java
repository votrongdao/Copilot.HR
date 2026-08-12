package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Employee entity querying PostgreSQL database.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
    Optional<Employee> findByEmployeeId(@Param("employeeId") String employeeId);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Employee> findByEmploymentStatus(String status);
}
