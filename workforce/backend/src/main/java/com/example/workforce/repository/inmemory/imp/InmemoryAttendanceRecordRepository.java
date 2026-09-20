package com.example.workforce.repository.inmemory.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.model.GetListAttendanceCorrections;
import com.example.workforce.repository.IAttendanceRecordRepository;

@Repository
@Profile("inmemory")
public class InmemoryAttendanceRecordRepository implements IAttendanceRecordRepository {
    Map<UUID, AttendanceRecordEntity> storages = new ConcurrentHashMap<>();

    @Override
    public AttendanceRecordEntity save(AttendanceRecordEntity attendanceRecordEntity) {
        if (attendanceRecordEntity.getId() == null) {
            attendanceRecordEntity.setId(UUID.randomUUID());
        }
        storages.put(attendanceRecordEntity.getId(), attendanceRecordEntity);
        return attendanceRecordEntity;
    }

    @Override
    public Optional<AttendanceRecordEntity> findById(UUID id) {
        return Optional.ofNullable(storages.get(id));
    }

    @Override
    public List<AttendanceRecordEntity> findAll() {
        return new ArrayList<>(storages.values());
    }

    @Override
    public List<AttendanceRecordEntity> findAll(GetListAttendanceCorrections filter) {
        // return storages.values()
        //         .stream()

        //         .filter(c -> filter.getStatus() == null
        //                 || c.getStatus().toString().equals(filter.getStatus()))

        //         .filter(c -> filter.getEmployeeId() == null
        //                 || c.getEmployeeId()
        //                         .equals(filter.getEmployeeId()))

        //         .filter(c -> filter.getDateFrom() == null
        //                 || !c.getSubmittedAt()
        //                         .toLocalDate()
        //                         .isBefore(filter.getDateFrom()))

        //         .filter(c -> filter.getDateTo() == null
        //                 || !c.getSubmittedAt()
        //                         .toLocalDate()
        //                         .isAfter(filter.getDateTo()))

        //         .toList();
        return null;
    }

}
