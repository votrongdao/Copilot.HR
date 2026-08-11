package com.example.workforce.leave.model;

import java.time.LocalDate;
import java.util.List;

public record CalendarDayDto(LocalDate date, List<CalendarLeaveDto> leaves) {
}