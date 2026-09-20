package com.example.workforce.model;

import java.time.LocalDate;

public record LeaveRequestUpdate(LocalDate startDate, LocalDate endDate, String reason) {
}
