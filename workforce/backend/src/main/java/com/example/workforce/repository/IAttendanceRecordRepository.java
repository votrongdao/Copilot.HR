package com.example.workforce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.model.GetListAttendanceCorrections;

public interface IAttendanceRecordRepository {
    AttendanceRecordEntity save(AttendanceRecordEntity attendanceRecordEntity);

    Optional<AttendanceRecordEntity> findById(UUID id);

    List<AttendanceRecordEntity> findAll();

    List<AttendanceRecordEntity> findAll(GetListAttendanceCorrections filter);

}
