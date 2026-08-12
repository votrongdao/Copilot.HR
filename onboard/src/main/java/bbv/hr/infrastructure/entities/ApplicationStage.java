package bbv.hr.infrastructure.entities.onboarding;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "application_stage")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationStage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "stage_name", length = 100)
    private String stageName;

    @Column(name = "stage_order", length = 50)
    private int stageOrder;

    @Column(name = "description", length = 255)
    private String description;

}
