package com.example.workforce.repository.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.workforce.entity.AttendanceCorrectionEntity;
import com.example.workforce.model.GetListAttendanceCorrections;

public interface IInmemoryAttendanceCorrectionRepository {
    AttendanceCorrectionEntity save(AttendanceCorrectionEntity attendanceCorrectionEntity);

    Optional<AttendanceCorrectionEntity> findById(UUID id);

    List<AttendanceCorrectionEntity> findAll();

    List<AttendanceCorrectionEntity> findAll(GetListAttendanceCorrections filter);
}