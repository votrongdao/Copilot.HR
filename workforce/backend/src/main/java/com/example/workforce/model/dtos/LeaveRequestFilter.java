package com.example.workforce.model.dtos;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaveRequestFilter {
    private UUID employeeId;
    private UUID leaveTypeId;
    private String status;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int page;
    private int pageSize;
}
