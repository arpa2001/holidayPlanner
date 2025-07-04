package com.arpajit.holidayPlanner.dto;

public class MonthYearRequest {
    private String year;
    private String month;

    public void setYear(String year) {
        this.year = year;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
}
