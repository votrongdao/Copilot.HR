package com.example.workforce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.workforce.entity.AttendanceCorrectionEntity;
import com.example.workforce.model.AttendanceCorrectionSummary;
import com.example.workforce.model.GetListAttendanceCorrections;

public interface IAttendanceCorrectionRepository {
    AttendanceCorrectionEntity save(AttendanceCorrectionEntity attendanceCorrectionEntity);

    Optional<AttendanceCorrectionEntity> findById(UUID id);

    List<AttendanceCorrectionEntity> findAll();

    List<AttendanceCorrectionEntity> findAll(GetListAttendanceCorrections filter);

    List<AttendanceCorrectionEntity> findByRecordId(UUID recordId);

    List<AttendanceCorrectionEntity> findByEmployeeId(UUID employeeId);

    AttendanceCorrectionSummary getAttendanceCorrectionSummary();
}