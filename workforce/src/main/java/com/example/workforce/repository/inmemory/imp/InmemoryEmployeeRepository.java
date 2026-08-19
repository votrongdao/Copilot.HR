package com.example.workforce.repository.inmemory.imp;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.entity.EmployeeEntity;
import com.example.workforce.repository.IEmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InmemoryEmployeeRepository implements IEmployeeRepository {
    Map<UUID, EmployeeEntity> employees = new ConcurrentHashMap<>();

    @Override
    public EmployeeEntity save(EmployeeEntity employeeEntity) {
        if (employeeEntity.getId() == null) {
            employeeEntity.setId(UUID.randomUUID());
        }
        employees.put(employeeEntity.getId(), employeeEntity);
        return employeeEntity;
    }

    @Override
    public Optional<EmployeeEntity> findById(UUID id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return employees.values().stream().toList();
    }
}
