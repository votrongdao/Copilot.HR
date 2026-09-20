package com.example.workforce.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workforce.entity.LeaveBalanceAdjustmentEntity;

public interface ILeaveBalanceAdjustmentRepository extends JpaRepository<LeaveBalanceAdjustmentEntity, UUID> {
    List<LeaveBalanceAdjustmentEntity> findByEmployeeIdAndLeaveTypeId(UUID employeeId, UUID leaveTypeId);
}
