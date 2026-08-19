package com.example.workforce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workforce.entity.LeaveRequestEntity;
import com.example.workforce.model.dtos.LeaveRequestFilter;

@Repository
public interface ILeaveRequestRepository extends JpaRepository<LeaveRequestEntity, UUID> {
}
