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
import com.example.workforce.model.AttendanceCorrectionSummary;
import com.example.workforce.model.GetListAttendanceCorrections;
import com.example.workforce.repository.inmemory.IAttendanceCorrectionRepository;

@Repository
@Profile("inmemory")
@Primary
public class InmemoryAttendanceCorrectionRepository implements IAttendanceCorrectionRepository {
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
                        || c.getStatus().toString().equals(filter.getStatus()))

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

    @Override
    public List<AttendanceCorrectionEntity> findByRecordId(UUID recordId) {
        return storages.values().stream().filter(c -> c.getAttendanceRecordId().equals(recordId)).toList();
    }

    @Override
    public List<AttendanceCorrectionEntity> findByEmployeeId(UUID employeeId) {
        return storages.values().stream().filter(c -> c.getEmployeeId().equals(employeeId)).toList();
    }

    @Override
    public AttendanceCorrectionSummary getAttendanceCorrectionSummary() {
        return storages.values().stream()
                .collect(AttendanceCorrectionSummary::new,
                        (summary, correction) -> {
                            if (correction.getStatus() == null) {
                                summary.setPendingReview(summary.getPendingReview() + 1);
                            } else if (correction.getStatus().toString().equals("APPROVED")
                                    && correction.getSubmittedAt().toLocalDate().equals(java.time.LocalDate.now())) {
                                summary.setApprovedToday(summary.getApprovedToday() + 1);
                            } else if (correction.getStatus().toString().equals("REJECTED")
                                    && correction.getSubmittedAt().toLocalDate().equals(java.time.LocalDate.now())) {
                                summary.setRejectedToday(summary.getRejectedToday() + 1);
                            }
                        },
                        (summary1, summary2) -> {
                            summary1.setPendingReview(summary1.getPendingReview() + summary2.getPendingReview());
                            summary1.setApprovedToday(summary1.getApprovedToday() + summary2.getApprovedToday());
                            summary1.setRejectedToday(summary1.getRejectedToday() + summary2.getRejectedToday());
                        });
    }
}
