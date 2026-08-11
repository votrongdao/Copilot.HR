package com.example.workforce.leave.model;

import java.time.LocalDate;
import java.util.UUID;

public record HolidayDto(UUID id, String holidayName, LocalDate holidayDate, String holidayType,
        String applicableLocation, String status) {
}