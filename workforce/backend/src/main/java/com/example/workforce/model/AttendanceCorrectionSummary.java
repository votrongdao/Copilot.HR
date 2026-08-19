package com.example.workforce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class AttendanceCorrectionSummary {
    private int pendingReview;
    private int approvedToday;
    private int rejectedToday;
}