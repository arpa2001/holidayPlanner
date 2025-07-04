package com.arpajit.holidayPlanner.dto;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class HolidayOutput {
    private LocalDate holidayDate;
    private String holidayName;
    private String holidayType;

    public HolidayOutput(LocalDate holidayDate, String holidayName, String holidayType) {
        this.holidayDate = holidayDate;
        this.holidayName = holidayName;
        this.holidayType = holidayType;
    }

    public String getHolidayDate() {return holidayDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));}
    public String getHolidayName() {return holidayName;}
    public String getHolidayType() {return holidayType;}
}
