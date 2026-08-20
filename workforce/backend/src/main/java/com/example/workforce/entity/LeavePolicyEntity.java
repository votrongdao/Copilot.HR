package com.example.workforce.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "leave_policy")
public class LeavePolicyEntity {
    @Id
    UUID id;
    @Column(name = "organization_id")
    UUID organizationId;
    @Column(name = "policy_name")
    String policyName;
    @Column(name = "leave_type_id")
    UUID leaveTypeId;
    @Column(name = "entitlement_value")
    Float entitlementValue;
    @Column(name = "entitlement_unit")
    String entitlementUnit;
    @Column(name = "accrual_method")
    String accrualMethod;
    @Column(name = "carry_over_value")
    Float carryOverValue;
    @Column(name = "carry_over_unit")
    String carryOverUnit;
    @Column(name = "eligibility")
    String eligibility;
    @Column(name = "status")
    String status;
}
