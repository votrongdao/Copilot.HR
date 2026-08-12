package com.copilothr.onboarding.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "position")

@Getter
@Setter
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private long id;

    @Column(name = "position_name", nullable = false, length = 100)
    private String positionName;

    @Column(name = "department_name", length = 100)
    private String departmentName;

    @Column(name = "employment_type", length = 100)
    private String employmentType;

    @Enumerated(EnumType.STRING)
    private PositionStatus status;
}
