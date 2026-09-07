package com.example.workforce.entity;

import java.sql.Date;
import java.util.UUID;

import com.example.workforce.enums.HolidayRecordStatus;
import com.example.workforce.enums.HolidayType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "holiday")
@Data
public class HolidayEntity {
    private UUID id;
    @Column(name = "organization_id")
    private UUID organizationId;
    @Column(name = "holiday_name")
    private String holidayName;
    @Column(name = "holiday_date")
    private Date holidayDate;
    @Column(name = "holiday_type")
    private HolidayType holidayType;
    @Column(name = "application_location")
    private String applicationLocation;
    @Column(name = "status")
    private HolidayRecordStatus status;
}
