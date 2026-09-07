package bbv.hr.infrastructure.repositories.organization;

import bbv.hr.infrastructure.entities.organization.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Department entity querying PostgreSQL database.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    /** Find department by department ID. */
    @Query("SELECT d FROM Department d WHERE d.departmentId = :departmentId")
    Optional<Department> findByDepartmentId(@Param("departmentId") String departmentId);

    /** Find root departments with no parent department. */
    List<Department> findByParentDepartmentIsNull();

    /** Find sub-departments belonging to a parent department. */
    List<Department> findByParentDepartmentDepartmentId(String parentId);


    /** Find departments by status. */
    List<Department> findByStatus(String status);
}
