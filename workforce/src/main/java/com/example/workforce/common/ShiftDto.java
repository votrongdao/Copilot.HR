package com.example.workforce.common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ShiftDto(UUID id, LocalDate shiftDate, LocalTime startTime, LocalTime endTime, int unpaidBreakMinutes) {
    public static ShiftDto sample(UUID id) {
        return new ShiftDto(id, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(17, 0), 60);
    }
}