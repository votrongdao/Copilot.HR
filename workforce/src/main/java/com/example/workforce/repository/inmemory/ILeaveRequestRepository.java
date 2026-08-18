package com.example.workforce.repository.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.workforce.entity.LeaveRequestEntity;
import com.example.workforce.model.dtos.LeaveRequestFilter;

public interface ILeaveRequestRepository {
    LeaveRequestEntity save(LeaveRequestEntity leaveRequestEntity);

    Optional<LeaveRequestEntity> findById(UUID id);

    List<LeaveRequestEntity> findAll();

    List<LeaveRequestEntity> findAll(LeaveRequestFilter filter);
}
