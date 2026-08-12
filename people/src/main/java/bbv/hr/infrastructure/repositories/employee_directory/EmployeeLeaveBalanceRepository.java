package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.EmployeeLeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for EmployeeLeaveBalance entity querying PostgreSQL database.
 */
@Repository
public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance, String> {

    @Query("SELECT b FROM EmployeeLeaveBalance b WHERE b.employee.employeeId = :employeeId")
    List<EmployeeLeaveBalance> findByEmployeeId(@Param("employeeId") String employeeId);
}
