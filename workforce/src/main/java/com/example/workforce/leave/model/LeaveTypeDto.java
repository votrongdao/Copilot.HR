package com.example.workforce.leave.model;

import java.util.UUID;

public record LeaveTypeDto(UUID id, String name, String status) {
}