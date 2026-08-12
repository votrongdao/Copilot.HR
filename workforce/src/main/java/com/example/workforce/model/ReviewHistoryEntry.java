package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ReviewHistoryEntry(UUID id, String action, EmployeeBrief actor, String comment,
        OffsetDateTime occurredAt) {
}