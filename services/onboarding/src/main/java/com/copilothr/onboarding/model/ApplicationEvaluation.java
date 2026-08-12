package com.copilothr.onboarding.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "application_evaluation")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "evaluation_id")
    private UUID evaluationId;

    @Column(name = "application_id", nullable = false)
    private UUID applicationId;

    @Column(name = "evaluator_user_id", nullable = false)
    private UUID evaluatorUserId;

    @Column(name = "technical_score")
    private Integer technicalScore;

    @Column(name = "experience_score")
    private Integer experienceScore;

    @Column(name = "communication_score")
    private Integer communicationScore;

    @Column(name = "culture_fit_score")
    private Integer cultureFitScore;

    @Column(name = "overall_score", precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(name = "recommendation")
    private String recommendation;

    @Lob
    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
