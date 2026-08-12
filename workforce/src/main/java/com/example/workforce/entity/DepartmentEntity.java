package com.example.workforce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "department")
public class DepartmentEntity {
    @Id
    private UUID id;

    private String name;

    protected DepartmentEntity() {
    }

    public DepartmentEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}