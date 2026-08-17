package com.example.workforce.repository.inmemory.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.workforce.entity.AttendanceCorrectionEntity;
import com.example.workforce.entity.AttendanceRecordEntity;
import com.example.workforce.model.GetListAttendanceCorrections;
import com.example.workforce.repository.inmemory.IInmemoryAttendanceCorrectionRepository;

@Repository
@Profile("inmemory")
@Primary
public class InmemoryAttendanceCorrectionRepository implements IInmemoryAttendanceCorrectionRepository {
    private final Map<UUID, AttendanceCorrectionEntity> storages = new ConcurrentHashMap<>();

    @Override
    public AttendanceCorrectionEntity save(AttendanceCorrectionEntity correction) {
        if (correction.getId() == null) {
            correction.setId(UUID.randomUUID());
        }
        storages.put(correction.getId(), correction);
        return correction;
    }

    @Override
    public Optional<AttendanceCorrectionEntity> findById(UUID id) {
        return Optional.ofNullable(storages.get(id));
    }

    @Override
    public List<AttendanceCorrectionEntity> findAll() {
        return new ArrayList<>(storages.values());
    }

    @Override
    public List<AttendanceCorrectionEntity> findAll(GetListAttendanceCorrections filter) {
        return storages.values()
                .stream()

                .filter(c -> filter.getStatus() == null
                        || c.getStatus() == filter.getStatus())

                .filter(c -> filter.getEmployeeId() == null
                        || c.getEmployeeId()
                                .equals(filter.getEmployeeId()))

                .filter(c -> filter.getDateFrom() == null
                        || !c.getSubmittedAt()
                                .toLocalDate()
                                .isBefore(filter.getDateFrom()))

                .filter(c -> filter.getDateTo() == null
                        || !c.getSubmittedAt()
                                .toLocalDate()
                                .isAfter(filter.getDateTo()))

                .toList();
    }

}
