package com.example.workforce.repository;

import com.example.workforce.entity.LeaveRequestEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequestEntity, UUID> {
}