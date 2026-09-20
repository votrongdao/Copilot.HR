package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for EmployeeDocument entity querying PostgreSQL database.
 */
@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, String> {

    @Query("SELECT d FROM EmployeeDocument d WHERE d.employee.employeeId = :employeeId")
    List<EmployeeDocument> findByEmployeeId(@Param("employeeId") String employeeId);
}
