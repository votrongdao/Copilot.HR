package bbv.hr.infrastructure.repositories.employee_directory;

import bbv.hr.infrastructure.entities.employee_directory.Asset;
import bbv.hr.infrastructure.repositories.GetData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-backed Repository for Asset entity querying GetData component with in-memory caching.
 */
@Repository
public class AssetRepository {

    private final GetData getData;
    private final List<Asset> assets = new ArrayList<>();

    public AssetRepository(GetData getData) {
        this.getData = getData;
    }

    /**
     * Retrieve all assigned asset records lazily cached from JSON mock data.
     */
    public List<Asset> findAll() {
        if (assets.isEmpty()) {
            List<Asset> loaded = getData.getEmployeeDirectoryEntities("asset", Asset.class);
            if (loaded != null) {
                assets.addAll(loaded);
            }
        }
        return assets;
    }

    /**
     * Find all assigned hardware assets for a specific employee ID.
     */
    public List<Asset> findByEmployeeId(String employeeId) {
        return findAll().stream()
                .filter(a -> a.getEmployee() != null && a.getEmployee().getEmployeeId() != null
                        && a.getEmployee().getEmployeeId().equalsIgnoreCase(employeeId))
                .collect(Collectors.toList());
    }
}
