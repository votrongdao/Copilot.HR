package com.example.workforce.model;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class GetListAttendanceCorrections {
    private String status;
    private UUID employeeId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;
    private int page = 1;
    private int pageSize = 20;
}
