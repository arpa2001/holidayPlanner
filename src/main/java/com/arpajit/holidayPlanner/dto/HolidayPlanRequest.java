package com.arpajit.holidayPlanner.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HolidayPlanRequest {
    private String startDate;
    private String endDate;
    private int leaves;
    private int daysOfHolidays;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }
    public void setDaysOfHolidays(int daysOfHolidays) {
        this.daysOfHolidays = daysOfHolidays;
    }

    public LocalDate getStartDate() {
        try {
            return startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("startDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered startDate. ERROR: "+e.getMessage());
        }
    }
    public LocalDate getEndDate() {
        try {
            return endDate != null ? LocalDate.parse(endDate) : LocalDate.of(LocalDate.now().getYear(), 12, 31);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("endDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered endDate. ERROR: "+e.getMessage());
        }
    }
    public int getLeaves() {return leaves;}
    public int getDaysOfHolidays() {
        if(daysOfHolidays==0)
            throw new IllegalArgumentException(
                "Please enter how many days of holidays you need in daysOfHolidays");
        else if(daysOfHolidays<leaves)
            throw new IllegalArgumentException(
                "Your Holiday of "+daysOfHolidays+" days can be anytime if you can take "+leaves+" leaves");
        else return daysOfHolidays;
    }
}
