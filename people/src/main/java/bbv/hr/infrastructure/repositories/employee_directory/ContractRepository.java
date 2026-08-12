package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Contract entity querying PostgreSQL database.
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    @Query("SELECT c FROM Contract c WHERE c.employee.employeeId = :employeeId")
    List<Contract> findByEmployeeId(@Param("employeeId") String employeeId);

    Optional<Contract> findByContractNumber(String contractNumber);
}
