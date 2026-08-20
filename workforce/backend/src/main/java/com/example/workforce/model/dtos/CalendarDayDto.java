package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class  CalendarDayDto {
    private LocalDate date;
    private List<CalendarLeaveDto> entries;
    private int pendingCount;
}
