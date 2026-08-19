package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.util.List;

public record CalendarDayDto(LocalDate date, List<CalendarLeaveDto> entries, int pendingCount) {
}
