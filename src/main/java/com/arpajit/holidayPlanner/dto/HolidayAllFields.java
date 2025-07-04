package com.arpajit.holidayPlanner.dto;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class HolidayAllFields {
    private String holidayId;
    private LocalDate holidayDate;
    private String holidayName;
    private String holidayType;
    private String holidaySource;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private String modifyRemarks;

    public HolidayAllFields(String holidayId,
                            LocalDate holidayDate,
                            String holidayName,
                            String holidayType,
                            String holidaySource,
                            LocalDateTime createdDate,
                            LocalDateTime modifiedDate,
                            String createdBy,
                            String modifiedBy,
                            String modifyRemarks) {
            this.holidayId = holidayId;
            this.holidayDate = holidayDate;
            this.holidayName = holidayName;
            this.holidayType = holidayType;
            this.holidaySource = holidaySource;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
            this.createdBy = createdBy;
            this.modifiedBy = modifiedBy;
            this.modifyRemarks = modifyRemarks;
    }

    public String getHolidayId() {return holidayId;}
    public String getHolidayDate() {return holidayDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));}
    public String getHolidayName() {return holidayName;}
    public String getHolidayType() {return holidayType;}
    public String getHolidaySource() {return holidaySource;}
    public LocalDateTime getCreatedDate() {return createdDate;}
    public LocalDateTime getModifiedDate() {return modifiedDate;}
    public String getCreatedBy() {return createdBy;}
    public String getModifiedBy() {return modifiedBy;}
    public String getModifyRemarks() {return modifyRemarks;}
}
