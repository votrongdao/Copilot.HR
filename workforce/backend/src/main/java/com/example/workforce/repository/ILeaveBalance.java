package com.example.workforce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workforce.entity.LeaveBalanceEntity;
import com.example.workforce.entity.LeaveRequestEntity;
import com.example.workforce.model.dtos.LeaveRequestFilter;

@Repository
public interface ILeaveBalance extends JpaRepository<LeaveBalanceEntity, UUID> {
    List<LeaveBalanceEntity> findByEmployeeId(UUID employeeId);

    LeaveBalanceEntity findByEmployeeIdAndLeaveTypeId(UUID employeeId, UUID leaveTypeId);
}
