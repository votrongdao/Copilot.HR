package com.example.workforce.common;

import java.util.UUID;

public record DepartmentDto(UUID id, String name) {
    public static DepartmentDto sample(UUID id) {
        return new DepartmentDto(id, "Sample Department");
    }
}