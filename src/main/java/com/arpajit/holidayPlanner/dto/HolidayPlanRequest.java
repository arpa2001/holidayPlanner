package com.arpajit.holidayPlanner.dto;

public class HolidayPlanRequest {
    private String startDate;
    private String endDate;
    private String leaves;
    private String daysOfHolidays;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }
    public void setDaysOfHolidays(String daysOfHolidays) {
        this.daysOfHolidays = daysOfHolidays;
    }

    public String getStartDate() {return startDate;}
    public String getEndDate() {return endDate;}
    public String getLeaves() {return leaves;}
    public String getDaysOfHolidays() {return daysOfHolidays;}
}
