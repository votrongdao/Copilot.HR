package bbv.hr.api.dtos.employee_directory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetResponse {

    private String assetId;
    private String assetName;
    private String serialNumber;
    private LocalDate assignedDate;
}
