package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewHistoryEntry {
        private UUID id;
        private String action;
        private EmployeeBrief actor;
        private String comment;
        private OffsetDateTime occurredAt;
}