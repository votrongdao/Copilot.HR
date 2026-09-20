package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Certification entity querying PostgreSQL database.
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {

    @Query("SELECT c FROM Certification c WHERE c.employee.employeeId = :employeeId")
    List<Certification> findByEmployeeId(@Param("employeeId") String employeeId);
}
