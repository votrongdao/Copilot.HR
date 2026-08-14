package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for LeaveType entity querying PostgreSQL database.
 */
@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, String> {

    Optional<LeaveType> findByCode(String code);
}
