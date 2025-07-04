package com.arpajit.holidayPlanner.dto;

import java.time.Month;
import java.time.Year;

public class MonthYearRequest {
    private int year;
    private String month;

    public void setYear(int year) {
        this.year = year;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year!=0 ? year : Year.now().getValue();
    }
    public int getMonth() {
        try {
            if(month==null) throw new IllegalArgumentException("month is missing");
            return Month.valueOf(month).getValue();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "month is not in correct format. Expected: Full month name all in UPPERCASE");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered month. ERROR: "+e.getMessage());
        }
    }
}
