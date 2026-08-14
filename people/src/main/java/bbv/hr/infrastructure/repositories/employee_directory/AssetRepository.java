package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Asset entity querying PostgreSQL database.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

    @Query("SELECT a FROM Asset a WHERE a.employee.employeeId = :employeeId")
    List<Asset> findByEmployeeId(@Param("employeeId") String employeeId);
}
