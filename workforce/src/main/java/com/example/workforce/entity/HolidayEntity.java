package com.example.workforce.entity;

import java.sql.Date;
import java.util.UUID;

import com.example.workforce.enums.HolidayRecordStatus;
import com.example.workforce.enums.HolidayType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "holiday")
@Data
public class HolidayEntity {
    private UUID id;
    private UUID organization_id;
    private String holiday_name;
    private Date holiday_date;
    private HolidayType holiday_type;
    private String application_location;
    private HolidayRecordStatus status;
}
