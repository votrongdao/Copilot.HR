package com.example.workforce.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class LeavePolicyEntity {
    UUID id;
    UUID organizationId;
    String policyName;
    UUID leaveTypeId;
    Float entitlementValue;
    String entitlementUnit;
    String accrualMethod;
    Float carryOverValue;
    String carryOverUnit;
    String eligibility;
    String status;
}
