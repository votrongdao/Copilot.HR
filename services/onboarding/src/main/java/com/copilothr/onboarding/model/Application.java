package com.copilothr.onboarding.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private UUID applicationId;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "position_id")
    private UUID positionId;

    @Column(name = "application_stage_id")
    private UUID applicationStageId;

    @Column(name = "owner_user_id")
    private UUID ownerUserId;

    @Column(name = "status")
    private String status;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;
}
