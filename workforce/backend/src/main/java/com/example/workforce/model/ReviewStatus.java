package com.example.workforce.model;

import com.example.workforce.common.EmployeeBrief;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewStatus {
        UUID correctionId;
        String status;
        EmployeeBrief reviewedBy;
        OffsetDateTime reviewedAt;
        String managerComment;

        public ReviewStatus(String status, OffsetDateTime reviewedAt, EmployeeBrief reviewedBy) {
                this.status = status;
                this.reviewedAt = reviewedAt;
                this.reviewedBy = reviewedBy;
        }
}