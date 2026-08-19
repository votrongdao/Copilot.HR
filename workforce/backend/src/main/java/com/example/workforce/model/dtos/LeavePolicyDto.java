package com.example.workforce.model.dtos;

import java.util.UUID;

public record LeavePolicyDto(UUID id, String policyName, UUID leaveTypeId, double entitlementValue,
        String entitlementUnit, String accrualMethod, double carryOverValue, String carryOverUnit, String eligibility,
        String status) {
    public static LeavePolicyDto sample(UUID id, UUID leaveTypeId) {
        return new LeavePolicyDto(id, "Default Annual Policy", leaveTypeId, 18, "Days", "annual_reset", 0, "Days",
                "All employees", "active");
    }
}
